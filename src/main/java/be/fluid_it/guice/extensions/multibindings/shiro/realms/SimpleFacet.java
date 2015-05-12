package be.fluid_it.guice.extensions.multibindings.shiro.realms;

public class SimpleFacet implements Facet {
    private final String value;

    public SimpleFacet(String value) {
        this.value = value;
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

    @Override
    public String value() {
        return this.value;
    }
}
