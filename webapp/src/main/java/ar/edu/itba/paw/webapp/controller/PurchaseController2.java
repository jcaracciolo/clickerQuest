package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.FactoryType;
import ar.edu.itba.paw.model.ResourceType;
import ar.edu.itba.paw.model.User;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by juanfra on 18/06/17.
 */
@Controller
public class PurchaseController2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseController2.class);

    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/buyFactory", method = { RequestMethod.POST })
    @ResponseBody
    public String purchaseFactory(Principal principal, @RequestParam("id") final int factoryId,
                                  @RequestParam("amount") final int amount){
        Boolean result = userService.purchaseFactory(
                userService.findByUsername(principal.getName()).getId(), FactoryType.fromId(factoryId),amount);
        JSONObject j = new JSONObject();
        j.put("result",result);
        j.put("type", "purchaseFactory");
        j.put("id",factoryId);
        return j.toJSONString();
    }

//    @RequestMapping(value = "/canBuyFactory", method = { RequestMethod.POST })
//    @ResponseBody
//    public String canPurchaseFactory(Principal principal){
//        long userID = userService.findByUsername(principal.getName()).getId();
//        Collection<BuyLimits> purchaseableFactory= userService.getPurchaseableFactory(userID);
//        JSONObject buyLimitArray = new JSONObject();
//        for (BuyLimits bl : purchaseableFactory){
//            buyLimitArray.put(bl.getFactoryType().getId(),buyLimitToJson(bl));
//        }
//        JSONObject j = new JSONObject();
//        j.put("buyables",buyLimitArray);
//        j.put("type", "canPurchaseFactory");
//        return j.toJSONString();
//    }

    @RequestMapping(value = "/upgradeFactory", method = { RequestMethod.POST })
    @ResponseBody
    public String upgradeFactory(Principal principal, @RequestParam("id") final int factoryId){
        long userId = userService.findByUsername(principal.getName()).getId();
        Boolean result = userService.purchaseUpgrade(userId, FactoryType.fromId(factoryId));
        long factoryLevel = userService.getUserFactories(userId).stream().filter(f -> f.getType().getId() == factoryId).findAny().get().getLevel();
        JSONObject j = new JSONObject();
        j.put("result",result);
        j.put("type", "upgradeFactory");
        j.put("id",factoryId);
        j.put("level",factoryLevel);
        return j.toJSONString();
    }

    @RequestMapping(value = "/buyFromMarket", method = { RequestMethod.POST })
    @ResponseBody
    public String buyFromMarket(Principal principal, @RequestParam("resourceId") final int resourceId,
                                @RequestParam("quantity")final double quantity) {
        User u = userService.findByUsername(principal.getName());
        ResourceType resource =  ResourceType.fromId(resourceId);
        Boolean result = false;
        if (resource != null) {
            result = userService.purchaseResourceType(u.getId(), resource, quantity);
        }
        JSONObject j = new JSONObject();
        j.put("result", result);
        j.put("type", "buyFromMarket");
        j.put("resourceId",resourceId);
        j.put("quantity", quantity);
        return j.toJSONString();
    }

    @RequestMapping(value = "/sellToMarket", method = { RequestMethod.POST })
    @ResponseBody
    public String sellToMarket(Principal principal, @RequestParam("resourceId") final int resourceId,
                               @RequestParam("quantity")final double quantity) {
        User u = userService.findByUsername(principal.getName());
        ResourceType resource =  ResourceType.fromId(resourceId);
        Boolean result = false;

        if (resource != null) {
            result = userService.sellResourceType(u.getId(), resource, quantity);
        }
        JSONObject j = new JSONObject();
        j.put("result", result);
        j.put("type", "sellToMarket");
        j.put("resourceId",resourceId);
        j.put("quantity", quantity);
        return j.toJSONString();
    }

}
