package be.fluid_it.guice.extensions.multibindings.shiro.realms;

import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.*;

public class RealmsResolver {
    private final Logger logger = LoggerFactory.getLogger(RealmsResolver.class);
    private Deployment.Option mode;
    private final Provider<Deployment.Option> modeProvider;
    private final Map<RealmKey, Realm> realmMap;

    @Inject
    public RealmsResolver(Map<RealmKey, Realm> realmMap, Provider<Deployment.Option> modeProvider) {
        logger.info("Available realms:");
        for (RealmKey realmKey : realmMap.keySet()) {
            logger.info(realmKey.name() + " facets " + Arrays.toString(realmKey.isCutBy()));
        }

        this.realmMap = realmMap;
        this.modeProvider = modeProvider;
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

    public Collection<Realm> activeRealms() {
        Collection<Realm> activeRealms = realms(new DeploymentSimpleFacet(mode()));
        logger.info("Active realms:");
        for (Realm realm : activeRealms) {
            logger.info(realm.getName());
        }
        return activeRealms;
    }

    private Deployment.Option mode() {
        if (mode == null) {
            mode = modeProvider.get();
            logger.info("Deployed as " + mode.name() + " ..." );
        }
        return mode;
    }
}