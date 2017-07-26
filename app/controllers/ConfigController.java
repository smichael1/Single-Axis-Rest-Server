package controllers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;


import com.typesafe.config.Config;

import com.typesafe.config.ConfigValue;


import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import services.ConfigElement;
import akka.actor.ActorSystem;
import akka.util.Timeout;

import javacsw.services.cs.akka.JConfigServiceClient;

import java.util.Map;
import java.io.File;
import java.util.ArrayList;

import java.util.List;



import java.util.Optional;


/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class ConfigController extends Controller {

	ActorSystem system;

	
	@Inject public ConfigController(ActorSystem system) throws Exception {
        	this.system = system;
            		
    }
	
    /**
     * An action that returns JSON for a list of assembly configs.
     * The configuration in the <code>routes</code> file maps the 
     * <code>GET</code> request with a path of <code>/config</code> 
     * to this method.
     */
    public Result getConfigs(String fileName) throws Exception {
    	
    	List<ConfigElement> configList = null;
    	
    	if (fileName.equals("singleAxis")) {
    	
    		configList = getConfigs("poc/singleAxis", "control-config");
    	
    	} else if (fileName.equals("galilHCD")) {
    		configList = getConfigs("poc/galilHCD", "axis-config");
    	}
     	
        return ok(Json.toJson(configList));
    }
    
    
    
   	private List<ConfigElement> getConfigs(String path, String configName) throws Exception {
	    // Get the trombone config file from the config service, or use the given resource file if that doesn't work
	    Timeout timeout = new Timeout(3, TimeUnit.SECONDS);
	    
	    File configFile = new File(path);
	    
	    Optional<Config> configOpt = JConfigServiceClient.getConfigFromConfigService(configFile,
	      Optional.empty(), Optional.empty(), system, timeout).get();
	    
	    if (configOpt.isPresent()) {
	    
	    	Config config = configOpt.get().getConfig(configName);
	    	
	    	List<ConfigElement> configList = new ArrayList<ConfigElement>();
	    	
	    	for (Map.Entry<String, ConfigValue> entry : config.entrySet()) {
	    		configList.add(new ConfigElement(entry.getKey(), config.getString(entry.getKey())));
	    	}
	       
	    	return configList;
	    	
	    } else {
	    	throw new Exception("Failed to get from config service: " + path );
	    }
	}

    
 

}
