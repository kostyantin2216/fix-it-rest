/**
 * 
 */
package com.fixit.rest.resources.general;

import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fixit.components.orders.OrderController;
import com.fixit.components.orders.OrderParams;
import com.fixit.components.orders.OrderRequestController;
import com.fixit.components.users.UserFactory;
import com.fixit.core.dao.mongo.TradesmanDao;
import com.fixit.core.dao.sql.ProfessionDao;
import com.fixit.core.dao.sql.TrafficSourceDao;
import com.fixit.core.data.OrderType;
import com.fixit.core.data.mongo.CommonUser;
import com.fixit.core.data.mongo.Tradesman;
import com.fixit.core.data.sql.Profession;
import com.fixit.core.data.sql.TrafficSource;
import com.fixit.core.utils.Constants;
import com.fixit.core.utils.Formatter;
import com.fixit.rest.resources.RestResource;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/11/10 15:51:17 GMT+2
 */
@Component
public class OrderHookResource implements RestResource {

	public final static String END_POINT = "orders";
	
	@Autowired
	private OrderController mOrderController;
	
	@Autowired
	private OrderRequestController mOrderReqController;
	
	@Autowired
	private UserFactory mUserFactory;
	
	@Autowired
	private ProfessionDao mProfessionDao;
	
	@Autowired
	private TradesmanDao mTradesmanDao;
	
	@Autowired
	private TrafficSourceDao mTrafficSourceDao;
	
	
	@POST
	@Path("directOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response directOrder(@FormParam("name") String name, @FormParam("email") String email, 
							    @FormParam("telephone") String telephone, @FormParam("profession") String profession, 
							    @FormParam("tradesmanId") String tradesmanId, @FormParam("address") String address,
							    @FormParam("comment") String comment, @FormParam("trafficSourceId") String trafficSourceId) {
		return createResponse(
				order(
					OrderType.DIRECT,
					name, 
					email,
					telephone, 
					profession, 
					tradesmanId, 
					address, 
					comment, 
					trafficSourceId, 
					(orderParams) -> mOrderController.orderTradesmen(orderParams)
				)
		);
	}	
	
	@POST
	@Path("directOrderRequest")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response directOrderRequest(@FormParam("name") String name, @FormParam("email") String email, 
							    @FormParam("telephone") String telephone, @FormParam("profession") String profession, 
							    @FormParam("tradesmanId") String tradesmanId, @FormParam("address") String address,
							    @FormParam("comment") String comment, @FormParam("trafficSourceId") String trafficSourceId) {
		return createResponse(
				order(
					OrderType.DIRECT,
					name, 
					email,
					telephone, 
					profession, 
					tradesmanId, 
					address, 
					comment, 
					trafficSourceId, 
					(orderParams) -> mOrderReqController.newRequest(orderParams)
				)
		);
	}	
	
	@POST
	@Path("quickOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response quickOrder(@FormParam("name") String name, @FormParam("email") String email, 
							   @FormParam("telephone") String telephone, @FormParam("profession") String profession, 
							   @FormParam("address") String address, @FormParam("comment") String comment,
							   @FormParam("trafficSourceId") String trafficSourceId) {
		return createResponse(
				order(
					OrderType.QUICK,
					name, 
					email, 
					telephone, 
					profession, 
					null,
					address, 
					comment, 
					trafficSourceId, 
					(orderParams) -> mOrderController.orderTradesmen(orderParams)
				)
		);
	}
	
	@POST
	@Path("quickOrderRequest")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response quickOrderRequest(@FormParam("name") String name, @FormParam("email") String email, 
							   @FormParam("telephone") String telephone, @FormParam("profession") String profession, 
							   @FormParam("address") String address, @FormParam("comment") String comment,
							   @FormParam("trafficSourceId") String trafficSourceId) {		
		return createResponse(
				order(
					OrderType.QUICK,
					name, 
					email, 
					telephone, 
					profession, 
					null,
					address, 
					comment, 
					trafficSourceId, 
					(orderParams) -> mOrderReqController.newRequest(orderParams)
				)
		);
	}
	
	private Response createResponse(String errorMsg) {
		if(errorMsg != null) {
			return Response.status(Status.BAD_REQUEST).entity(errorMsg).build();
		} else {
			return Response.ok().build();
		}
	}
	
	private String order(OrderType orderType, String name, String email, String telephone, String profession, String tradesmanId, String address, 
			   String comment, String trafficSourceId, Consumer<OrderParams> completeAction) {
		telephone = Formatter.normalizeTelephone(telephone);
		
		String errorMsg = null;
		OrderPreperation preperation = prepare(name, telephone, profession, address, trafficSourceId);
		
		if(StringUtils.isEmpty(preperation.errorMsg)) {
			if(orderType == OrderType.DIRECT && StringUtils.isEmpty(tradesmanId)) {
				errorMsg = "Missing tradesman";
			} else {
				OrderParams.Builder opBuilder = new OrderParams.Builder(orderType)
						.forProfession(preperation.profession)
						.atAddress(address)
						.withComment(comment);
				
				if(orderType == OrderType.DIRECT) {
					Tradesman tradesman = mTradesmanDao.findById(new ObjectId(tradesmanId));
					
					if(tradesman == null) {
						errorMsg = "Provided tradesman does not exist";
					} else if(!tradesman.isActive()) {
						errorMsg = "Provided tradesman is inactive";
					} else {
						opBuilder.withTradesman(tradesman);
					}
				}
				
				if(StringUtils.isEmpty(errorMsg)) {
					TrafficSource ts = mTrafficSourceDao.findById(Integer.parseInt(trafficSourceId));
					if(ts != null) {					
						CommonUser user = mUserFactory.createOrFindCommonUser(name, email, telephone);
						Observable.just(
								opBuilder.byUser(user)
										 .fromTrafficSource(ts)
										 .build()
						  )
						  .subscribeOn(Schedulers.io())
						  .subscribe(completeAction);
					} else {
						errorMsg = "Could not find traffic source with id: " + trafficSourceId;
					}
				}
			}
		} else {
			errorMsg = preperation.errorMsg;
		}
		
		return errorMsg;
	}
	
	private OrderPreperation prepare(String name, String telephone, String profession, String address, String trafficSourceId) {
		Profession actualProfession = null;
		String errorMsg = null;
		
		if(StringUtils.isEmpty(trafficSourceId)) {
			errorMsg = "Missing trafficSourceId";
		} else if(!Formatter.isInteger(trafficSourceId)){
			errorMsg = "trafficSourceId must be a valid integer";
		} else if(StringUtils.isEmpty(address)) {
			errorMsg = "Missing address";
		} if(StringUtils.isEmpty(name)) {
			errorMsg = "Missing name";
		} else if(StringUtils.isEmpty(telephone)) {
			errorMsg = "Missing telephone";
		} else if(!Formatter.isSaTelephone(telephone)) {
			errorMsg = "Invalid telephone provided";
		} else {
			if(StringUtils.isEmpty(profession)) {
				errorMsg = "Missing profession";
			} else {
				actualProfession = mProfessionDao.findByName(profession);
				if(actualProfession == null) {
					errorMsg = "Provided profession does not exist";
				} else if(!actualProfession.getIsActive()) {
					errorMsg = "Provided profession is inactive";
				}
			}
		}
		
		return new OrderPreperation(actualProfession, errorMsg);
	}
	
	private static class OrderPreperation {
		final Profession profession;
		final String errorMsg;
		
		OrderPreperation(Profession profession, String errorMsg) {
			this.profession = profession;
			this.errorMsg = errorMsg;
		}
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}

	public static void main(String[] args) {
		Pattern ptrn = Pattern.compile(Constants.RGX_SA_TELEPHONE);
		
		String[] tests = new String[]{
				"+27123456789",
				"+27628911340",
				"0628911340",
				"+21123456789",
				"12345678",
				"6289113401"
		};
		
		for(String test : tests) {
			System.out.println(test + " : " + ptrn.matcher(test).matches());
		}
	}
	
}
