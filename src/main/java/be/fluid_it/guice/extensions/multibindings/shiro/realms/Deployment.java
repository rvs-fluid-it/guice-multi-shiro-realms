package be.fluid_it.guice.extensions.multibindings.shiro.realms;

public interface Deployment extends Facet {
    public enum Option {
        FAT_JAR, WAR;
    }
}
