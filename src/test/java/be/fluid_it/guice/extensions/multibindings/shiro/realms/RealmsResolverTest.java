package be.fluid_it.guice.extensions.multibindings.shiro.realms;

import be.fluid_it.guice.extensions.multibindings.shiro.realms.modules.BindUtil;
import be.fluid_it.guice.extensions.multibindings.shiro.realms.modules.MultiRealmBinder;
import com.google.inject.*;
import com.google.inject.multibindings.MapBinder;
import org.junit.Assert;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.realm.Realm;
import org.junit.Test;

import java.util.Collection;

public class RealmsResolverTest {
    private static class ADummyShiroRealm implements Realm {
        @Override
        public String getName() {
            return getClass().getSimpleName();
        }

        @Override
        public boolean supports(AuthenticationToken token) {
            return false;
        }

        @Override
        public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
            return null;
        }
    }
    private static class AnotherDummyShiroRealm extends ADummyShiroRealm {
    }
    private static class YetAnotherDummyShiroRealm extends ADummyShiroRealm {
    }

    @Test
    public void testMultibindedShiroRealms() {
        Module coreModule = new Module() {
            @Override
            public void configure(Binder binder) {
                MultiRealmBinder.newMultiRealmBinder(binder)
                        .addBinding(new RealmKey(ADummyShiroRealm.class))
                        .to(ADummyShiroRealm.class);
            }
        };
        Module anotherRealmModule = new Module() {
            @Override
            public void configure(Binder binder) {
                MultiRealmBinder.newMultiRealmBinder(binder)
                        .addBinding(new RealmKey(AnotherDummyShiroRealm.class).cut(new DeploymentSimpleFacet(Deployment.Option.FAT_JAR)))
                        .to(AnotherDummyShiroRealm.class);
            }
        };
        Module yetAnotherRealmModule = new Module() {
            @Override
            public void configure(Binder binder) {
                MultiRealmBinder.newMultiRealmBinder(binder)
                        .addBinding(new RealmKey(AnotherDummyShiroRealm.class).cut(new DeploymentSimpleFacet(Deployment.Option.WAR)))
                        .to(YetAnotherDummyShiroRealm.class);
            }
        };
        Injector injector = Guice.createInjector(coreModule, anotherRealmModule, yetAnotherRealmModule);

        RealmsResolver realmsResolver = injector.getInstance(RealmsResolver.class);

        Assert.assertNotNull(realmsResolver);
        Collection<Realm> allRealms = realmsResolver.realms();
        Assert.assertNotNull(allRealms);
        Assert.assertEquals(3, allRealms.size());
        Collection<Realm> warRealms = realmsResolver.realms(new DeploymentSimpleFacet(Deployment.Option.WAR));
        Assert.assertNotNull(warRealms);
        Assert.assertEquals(2, warRealms.size());
        for (Realm realm : warRealms) {
            Assert.assertFalse(realm instanceof AnotherDummyShiroRealm);
        }
        Collection<Realm> fatJarRealms = realmsResolver.realms(new DeploymentSimpleFacet(Deployment.Option.FAT_JAR));
        Assert.assertNotNull(fatJarRealms);
        Assert.assertEquals(2, fatJarRealms.size());
        for (Realm realm : fatJarRealms) {
            Assert.assertFalse(realm instanceof YetAnotherDummyShiroRealm);
        }
    }

}
