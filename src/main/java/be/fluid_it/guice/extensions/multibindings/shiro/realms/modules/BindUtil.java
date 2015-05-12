package be.fluid_it.guice.extensions.multibindings.shiro.realms.modules;

import be.fluid_it.guice.extensions.multibindings.shiro.realms.Deployment;
import be.fluid_it.guice.extensions.multibindings.shiro.realms.DeploymentSimpleFacet;
import be.fluid_it.guice.extensions.multibindings.shiro.realms.RealmKey;
import com.google.inject.Binder;
import com.google.inject.multibindings.MapBinder;
import org.apache.shiro.realm.Realm;

public class BindUtil {
    public static void bind(Binder binder, RealmKey key, Class<? extends Realm> realmClazz) {
        MapBinder<RealmKey, Realm> realmBinder
                = MapBinder.newMapBinder(binder, RealmKey.class, Realm.class);
        realmBinder.addBinding(key).to(realmClazz);
    }
}
