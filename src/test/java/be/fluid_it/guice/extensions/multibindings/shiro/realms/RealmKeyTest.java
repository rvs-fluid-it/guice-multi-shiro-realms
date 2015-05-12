package be.fluid_it.guice.extensions.multibindings.shiro.realms;

import org.junit.Assert;
import org.junit.Test;

public class RealmKeyTest {
    private class ASimpleFacet extends SimpleFacet {
        public ASimpleFacet(String value) {
            super(value);
        }
    }
    private class AnotherSimpleFacet extends SimpleFacet {
        public AnotherSimpleFacet(String value) {
            super(value);
        }
    }

    @Test
    public void checkEquality() {
        RealmKey aKey = new RealmKey("theName").cut(new ASimpleFacet("aValue")).cut(new AnotherSimpleFacet("anotherValue"));
        RealmKey aSimilarKey = new RealmKey("theName").cut(new AnotherSimpleFacet("anotherValue")).cut(new ASimpleFacet("aValue"));
        Assert.assertFalse(aKey == aSimilarKey);
        Assert.assertEquals(aKey, aSimilarKey);
    }

    @Test
    public void checkEqualityDeploymentOption() {
        RealmKey fatJarDeploymentKey = new RealmKey("theName").cut(new DeploymentSimpleFacet(Deployment.Option.FAT_JAR));
        RealmKey warDeploymentKey = new RealmKey("theName").cut(new DeploymentSimpleFacet(Deployment.Option.WAR));
        Assert.assertNotEquals(fatJarDeploymentKey, warDeploymentKey);
        Assert.assertEquals(fatJarDeploymentKey, new RealmKey("theName").cut(new DeploymentSimpleFacet(Deployment.Option.FAT_JAR)));
    }

    @Test
    public void aRealmKeyMatchesAFacetIfItIsNotCutByTheFacetNameOrIfTheFacetValueIsEqual() {
        Assert.assertTrue(new RealmKey("dummyName").match(new DeploymentSimpleFacet(Deployment.Option.WAR)));
        Assert.assertTrue(new RealmKey("dummyName").cut(new DeploymentSimpleFacet(Deployment.Option.WAR)).match(new DeploymentSimpleFacet(Deployment.Option.WAR)));
        Assert.assertFalse(new RealmKey("dummyName").cut(new DeploymentSimpleFacet(Deployment.Option.FAT_JAR)).match(new DeploymentSimpleFacet(Deployment.Option.WAR)));
    }
}
