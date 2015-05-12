package be.fluid_it.guice.extensions.multibindings.shiro.realms;

import org.apache.shiro.realm.Realm;

import javax.inject.Inject;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RealmsResolver {
    private final Map<RealmKey, Realm> realmMap;

    @Inject
    public RealmsResolver(Map<RealmKey, Realm> realmMap) {
        this.realmMap = realmMap;
    }

    public Collection<Realm> realms() {
        return realmMap.values();
    }


    public Collection<Realm> realms(Facet... facets) {
        List<Realm> realms = new LinkedList<>();
        for (Map.Entry<RealmKey, Realm> entry : realmMap.entrySet()) {
            boolean matches = true;
            for (Facet facet : facets) {
                if (! entry.getKey().match(facet)) {
                   matches = false;
                   break;
                }
            }
            if (matches) { realms.add(entry.getValue()); }

        }
        return realms;
    }

}
