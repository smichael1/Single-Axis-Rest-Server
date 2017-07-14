package app.modules;

import com.google.inject.AbstractModule;

public class InitModule extends AbstractModule {

	@Override protected void configure() {
		bind(Init.class).to(InitLocationService.class).asEagerSingleton();
	}
	
}




