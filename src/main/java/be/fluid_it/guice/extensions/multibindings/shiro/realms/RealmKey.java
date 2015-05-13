package be.fluid_it.guice.extensions.multibindings.shiro.realms;

import com.google.common.collect.ImmutableMap;
import org.apache.shiro.realm.Realm;

import java.util.Map;

public class RealmKey {
    private final String name;
    private final Map<String, String> facets;

    public RealmKey(String name) {
        this.name = name;
        this.facets = ImmutableMap.of();
    }
    public RealmKey(Class<? extends Realm> realmClazz) {
        this(realmClazz.getSimpleName());
    }
    private RealmKey(RealmKey keyToCut, Facet facet) {
        this.name = keyToCut.name;
        this.facets = new ImmutableMap.Builder<String, String>().putAll(keyToCut.facets).put(facet.name(), facet.value()).build();
    }

    public RealmKey cut(Facet facet) {
        return new RealmKey(this,facet);
    }

    public boolean isCutBy(String name) {
       return facets.keySet().contains(name);
    }

    public boolean isCutBy(Class<? extends SimpleFacet> facetClazz) {
        return isCutBy(facetClazz.getSimpleName());
    }

    public boolean isCutBy(Facet facet) {
        return isCutBy(facet.name()) && facets.get(facet.name()).equals(facet.value());
    }

    public boolean match(Facet facet) {
        return !isCutBy(facet.name()) || isCutBy(facet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RealmKey realmKey = (RealmKey) o;

        if (!facets.equals(realmKey.facets)) return false;
        if (!name.equals(realmKey.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + facets.hashCode();
        return result;
    }
}
