package org.hni.admin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hni.common.Constants;
import org.hni.common.exception.HNIException;
import org.hni.provider.om.Menu;
import org.hni.provider.om.MenuItem;
import org.hni.provider.service.MenuService;
import org.hni.service.helpers.ProviderResourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/menus", description = "Operations on Menus and MenuItems")
@Component
@Path("/menus")
public class MenuServiceController extends AbstractBaseController {
	private static final Logger logger = LoggerFactory.getLogger(MenuServiceController.class);
	
	@Inject private MenuService menuService;
	
	@Inject private ProviderResourceHelper providerResHelper;
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns the menu with the given id"
		, notes = ""
		, response = Menu.class
		, responseContainer = "")
	public Menu getMenu(@PathParam("id") Long id) {
		return menuService.get(id);
	}

	@POST
	@Path("/menu/create")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Creates a new Menu or saves the Menu with the given id associated with the given Provider"
		, notes = "An Menu without an ID field will be created"
		, response = Menu.class
		, responseContainer = "")
	public String saveOrder(Menu menu) {
		if (getLoggedInUser() != null) {
			return providerResHelper.createMenu(menu, getLoggedInUser());
		}
		throw new HNIException("You must have elevated permissions to do this.");
	}

	@DELETE
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Deletes the Menu with the given id"
		, notes = ""
		, response = Menu.class
		, responseContainer = "")
	public Menu getDelete(@PathParam("id") Long id) {
		if (isPermitted(Constants.MENU, Constants.DELETE, id)) {
			return menuService.delete(new Menu(id));
		}
		throw new HNIException("You must have elevated permissions to do this.");
	}

	@GET
	@Path("/providers/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Returns a collection of Menu for the given organization"
	, notes = ""
	, response = Menu.class
	, responseContainer = "")
	public Collection<Menu> getMenus(@PathParam("id") Long id) {		
		return menuService.getAllByProviderId(id);
	}

	@POST
	@Path("/{id}/menuitems/add")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Creates a new MenuItem or saves the MenuItem"
		, notes = "An MenuItem without an ID field will be created"
		, response = Menu.class
		, responseContainer = "")
	public String saveMenuItem(@PathParam("id") Long menuId, MenuItem menuItem) {
		if (getLoggedInUser() != null) {
			menuItem.setMenu(new Menu(menuId));
			return providerResHelper.addMenuItemToMenu(menuId, menuItem);
			
		}
		throw new HNIException("You must have elevated permissions to do this.");
	}
	
	@POST
	@Path("/{id}/menuitems/list/add")
	@ApiOperation(value = "Updates the existing MenuItem"
		, notes = "An existing menu item will be updated."
		, response = Menu.class
		, responseContainer = "")
	public Response saveMenuItem(@PathParam("id") Long menuId, List<MenuItem> menuItems) {
		Map<String, String> response = new HashMap<>();
		if (getLoggedInUser() != null) {
			try{
				providerResHelper.updateMenuItemsToMenu(menuId, menuItems);
				response.put(Constants.MESSAGE,"Updated successfullly.");
			}catch(Exception e){
				response.put(Constants.MESSAGE,"Save Failed.");
			}
			
		}else{
			throw new HNIException("You must have elevated permissions to do this.");
		}
		return Response.ok(response).build();
	}

	@DELETE
	@Path("/{id}/menuitems/{miid}")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Creates a new MenuItem or saves the MenuItem"
		, notes = "An MenuItem without an ID field will be created"
		, response = Menu.class
		, responseContainer = "")
	public Menu deleteMenuItem(@PathParam("id") Long id, @PathParam("miid") Long miid) {
		if (isPermitted(Constants.MENU, Constants.DELETE, id)) {
			Menu menu = menuService.get(id);
			if ( null != menu ) {
				MenuItem menuItem = new MenuItem(miid);
				menu.getMenuItems().remove(menuItem);
			}
			return menuService.save(menu);
		}
		throw new HNIException("You must have elevated permissions to do this.");
	}
	
	@GET
	@Path("/{menuId}/menuItems/info")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Return menu items for the given menu id with column headers"
		, notes = "Return menuitems with headers"
		, response = MenuItem.class
		, responseContainer = "")
	public Response getMenuItemsWithHeader(@PathParam("menuId") Long menuId) {
		if (getLoggedInUser() == null) {
			throw new HNIException("You must have elevated permissions to do this.");
		}
		return Response.ok(providerResHelper.getMenuItemsWithHeaders(menuId)).build();
	}
	
	@POST
	@Path("/update")
	@ApiOperation(value = "Update menu for given menu id"
		, notes = "Returns menu"
		, response = Menu.class
		, responseContainer = "")
	public Response updateMenu(List<Menu> menu) {
		if (getLoggedInUser() == null) {
			throw new HNIException("You must have elevated permissions to do this.");
		}
		return Response.ok(providerResHelper.updateMenu(menu)).build();
	}
	
	@DELETE
	@Path("/{menuId}/delete")
	@ApiOperation(value = "Delete list of menus for given menu id"
		, notes = "Returns menu"
		, response = Menu.class
		, responseContainer = "")
	public Response deleteMenuList(@PathParam("menuId")List<Long> menuId) {
		if (getLoggedInUser() == null) {
			throw new HNIException("You must have elevated permissions to do this.");
		}
		
		return Response.ok(providerResHelper.deleteMenuList(menuId)).build();
	}
	
	@DELETE
	@Path("/{menuItemId}/menuItem/delete")
	@ApiOperation(value = "Delete list of menus for given menu id"
		, notes = "Returns menu"
		, response = Menu.class
		, responseContainer = "")
	public Response deleteMenuItem(@PathParam("menuItemId") Long menuItemId) {
		if (getLoggedInUser() == null) {
			throw new HNIException("You must have elevated permissions to do this.");
		}
		
		return Response.ok(providerResHelper.deleteMenuItem(menuItemId)).build();
	}

}
