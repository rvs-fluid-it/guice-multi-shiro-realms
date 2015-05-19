package be.fluid_it.guice.extensions.multibindings.shiro.realms.modules;

import be.fluid_it.guice.extensions.multibindings.shiro.realms.RealmKey;
import com.google.inject.Binder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiRealmBinder {
    private final MapBinder<RealmKey, Realm> mapBinder;

    private MultiRealmBinder(MapBinder<RealmKey, Realm> mapBinder) {
        this.mapBinder = mapBinder;
    }

    public static MultiRealmBinder newMultiRealmBinder(Binder binder) {
        return new MultiRealmBinder(MapBinder.newMapBinder(binder, RealmKey.class, Realm.class));
    }

    public LinkedBindingBuilder<Realm> addBinding(RealmKey key) {
        return this.mapBinder.addBinding(key);
    }
}
