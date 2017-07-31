package controllers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.Config;

import com.typesafe.config.ConfigValue;


import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.CommandArg;
import services.ConfigElement;
import akka.actor.ActorSystem;
import akka.util.Timeout;

import javacsw.services.cs.akka.JConfigServiceClient;
import javacsw.services.cs.IConfigData;
import javacsw.services.cs.akka.JBlockingConfigServiceClient;
import javacsw.services.cs.akka.JConfigServiceFactory;
import javacsw.services.cs.core.JConfigData;

import csw.services.cs.core.ConfigData;
import csw.services.cs.core.ConfigId;

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
    	
    		configList = getConfigs("poc/singleAxis", "org.tmt.aps.ics.singleAxis.assembly.control-config");
    	
    	} else if (fileName.equals("galilHCD")) {
    		configList = getConfigs("poc/galilHCD", "org.tmt.aps.ics.galilHCD.axis-config");
    	}
     	
        return ok(Json.toJson(configList));
    }
    
    public Result updateConfigs() throws Exception {
    	
       
    	JsonNode json = request().body().asJson();
      
  	  	if(json == null) {
  	  		return badRequest("Expecting Json data");
  	  	}
  	    
  	    String fileName = json.findPath("configTarget").textValue();
		//JsonNode argsNode = json.findPath("config");
		
		List<ConfigElement> updateList = new ArrayList<ConfigElement>();
		for (JsonNode configNode : json.withArray("config")) {
		    System.out.println("name -> " + configNode.get("name").asText());
		    System.out.println("value -> " + configNode.get("value").asText());
			
			updateList.add(new ConfigElement(configNode.get("name").asText(), configNode.get("value").asText()));
		}
		
		
		if (fileName.equals("singleAxis")) {
		
			String prefix = "org.tmt.aps.ics.singleAxis.assembly.control-config";
			String path = "poc/singleAxis";
			readAndUpdate(path, prefix, updateList);
			
		} else if (fileName.equals("galilHCD")) {
			
			String prefix = "org.tmt.aps.ics.galilHCD.axis-config";
			String path = "poc/galilHCD";
			readAndUpdate(path, prefix, updateList);
		}
		
		return ok("");
    }
    
    private void readAndUpdate(String path, String prefix, List<ConfigElement> updateList) throws Exception {
		
    	List<ConfigElement> configList = getConfigs(path, prefix);
		
		// fold in updates
		List<ConfigElement> updatedConfigList = updateConfigList(updateList, configList);
		
		// convert to config object
		String content = prefix + " " + unexpandNameValue(updatedConfigList);
		System.out.println("content = " + content);
		
		// update configuration
		String result = updateConfigFile(path, content);
		
		System.out.println("result = " + result);

    }
    
    
    private List<ConfigElement> updateConfigList(List<ConfigElement> newConfigs, List<ConfigElement> configElements) {
    	ArrayList<ConfigElement> newList = new ArrayList<ConfigElement>();
    	for (ConfigElement newConfig : newConfigs) {
	    	for (ConfigElement candidate : configElements) {
	    		if (candidate.getName().equals(newConfig.getName())) {
	    			newList.add(newConfig);
	    		} else {
	    			newList.add(candidate);
	    		}
	    	}
    	}
    	return newList;
    }
    
    
    private String unexpandNameValue(List<ConfigElement> configList) {
    	
    	StringBuffer buf = new StringBuffer();
    	
    	buf.append("{");
    	for (ConfigElement element : configList) {
    		buf.append("\"" + element.getName() + "\":\"" + element.getValue() + "\",");
    	}
    	buf.append("}");
    
    	return buf.toString();
    }
    
    
    private String updateConfigFile(String path, String data) throws Exception {
    	
	    Timeout timeout = new Timeout(3, TimeUnit.SECONDS);
	    
	    File configFile = new File(path);

    	JBlockingConfigServiceClient client = JConfigServiceFactory.getBlockingConfigServiceClient(system, timeout);
        
        ConfigId id = client.update(configFile, IConfigData.create(data), "user interface update");
        
        return id.toString();

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
