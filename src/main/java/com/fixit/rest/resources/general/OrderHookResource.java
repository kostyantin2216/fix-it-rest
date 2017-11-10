/**
 * 
 */
package com.fixit.rest.resources.general;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fixit.components.orders.QuickOrder;
import com.fixit.components.orders.QuickOrderController;
import com.fixit.core.dao.sql.ProfessionDao;
import com.fixit.core.data.sql.Profession;
import com.fixit.rest.resources.RestResource;

/**
 * @author 		Kostyantin
 * @createdAt 	2017/11/10 15:51:17 GMT+2
 */
@Component
public class OrderHookResource implements RestResource {

	public final static String END_POINT = "orders";
	
	@Autowired
	private QuickOrderController mQuickOrderController;
	
	@Autowired
	private ProfessionDao mProfessionDao;
	
	@POST
	@Path("quickOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response quickOrder(@FormParam("name") String name, @FormParam("email") String email, 
							   @FormParam("telephone") String telephone, @FormParam("profession") String profession, 
							   @FormParam("address") String address, @FormParam("latitude") Float latitude,
							   @FormParam("location") Float longitude) {
		
		Profession actualProfession = mProfessionDao.findByName(profession);
		if(actualProfession == null || !actualProfession.getIsActive()) {
			return Response.status(Status.BAD_REQUEST).entity("Invalid profession provided for order.").build();
		} else {
			QuickOrder quickOrder = new QuickOrder(name, email, telephone, actualProfession, address, latitude, longitude);
			if(quickOrder.isValid()) {
				mQuickOrderController.doOrder(quickOrder);
				return Response.ok().build();
			} else {
				return Response.status(Status.BAD_REQUEST).entity("Not enough data provided for order.").build();
			}
		}
	}

	@Override
	public String getEndPoint() {
		return END_POINT;
	}
	
}
