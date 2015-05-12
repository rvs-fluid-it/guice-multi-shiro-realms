package be.fluid_it.guice.extensions.multibindings.shiro.realms;

public class DeploymentSimpleFacet extends SimpleFacet implements Deployment {

    public DeploymentSimpleFacet(Option deploymentOption) {
        super(deploymentOption.name());
    }
}
