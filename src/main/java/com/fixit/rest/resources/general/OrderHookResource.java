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
import com.fixit.components.users.UserFactory;
import com.fixit.core.dao.mongo.TradesmanDao;
import com.fixit.core.dao.sql.ProfessionDao;
import com.fixit.core.data.mongo.CommonUser;
import com.fixit.core.data.mongo.Tradesman;
import com.fixit.core.data.sql.Profession;
import com.fixit.core.utils.Constants;
import com.fixit.core.utils.Formatter;
import com.fixit.rest.resources.RestResource;

import io.reactivex.Observable;
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
	private UserFactory mUserFactory;
	
	@Autowired
	private ProfessionDao mProfessionDao;
	
	@Autowired
	private TradesmanDao mTradesmanDao;
	
	@POST
	@Path("quickOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response quickOrder(@FormParam("name") String name, @FormParam("email") String email, 
							   @FormParam("telephone") String telephone, @FormParam("profession") String profession, 
							   @FormParam("address") String address, @FormParam("comment") String comment) {
		telephone = Formatter.normalizeTelephone(telephone);

		String errorMsg = null;
		OrderPreperation preperation = prepare(name, telephone, profession, address);
	
		if (StringUtils.isEmpty(preperation.errorMsg)) {
			CommonUser user = mUserFactory.createOrFindCommonUser(name, email, telephone);
			
			Observable.just(OrderParams.quickOrder(user, preperation.profession, address, comment))
					  .subscribeOn(Schedulers.io())
					  .subscribe(orderParams -> {
						  mOrderController.quickOrder(orderParams);
					  });
		} else {
			errorMsg = preperation.errorMsg;
		}
		
		return createResponse(errorMsg);
	}
	
	@POST
	@Path("directOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response directOrder(@FormParam("name") String name, @FormParam("email") String email, 
							    @FormParam("telephone") String telephone, @FormParam("profession") String profession, 
							    @FormParam("tradesmanId") String tradesmanId, @FormParam("address") String address,
							    @FormParam("comment") String comment) {
		telephone = Formatter.normalizeTelephone(telephone);

		String errorMsg = null;
		OrderPreperation preperation = prepare(name, telephone, profession, address);
		
		if(StringUtils.isEmpty(preperation.errorMsg)) {
			if(StringUtils.isEmpty(tradesmanId)) {
				errorMsg = "Missing tradesman";
			} else {
				Tradesman tradesman = mTradesmanDao.findById(new ObjectId(tradesmanId));
				
				if(tradesman == null) {
					errorMsg = "Provided tradesman does not exist";
				} else if(!tradesman.isActive()) {
					errorMsg = "Provided tradesman is inactive";
				} else {
					CommonUser user = mUserFactory.createOrFindCommonUser(name, email, telephone);
					
					Observable.just(OrderParams.directOrder(user, tradesman, preperation.profession, address, comment))
					  .subscribeOn(Schedulers.io())
					  .subscribe(orderParams -> {
							mOrderController.directOrder(orderParams);
					  });
				}
			}
		} else {
			errorMsg = preperation.errorMsg;
		}
		
		return createResponse(errorMsg);
	}	
	
	private Response createResponse(String errorMsg) {
		if(errorMsg != null) {
			return Response.status(Status.BAD_REQUEST).entity(errorMsg).build();
		} else {
			return Response.ok().build();
		}
	}
	
	private OrderPreperation prepare(String name, String telephone, String profession, String address) {
		Profession actualProfession = null;
		String errorMsg = null;
		
		if(StringUtils.isEmpty(address)) {
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
