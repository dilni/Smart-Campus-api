package com.smartcampus.model;

import java.util.ArrayList;
import java.util.List;

public class ApiMetadata {
    private String version;
    private String description;
    private List<Link> _links;

    public ApiMetadata() {
        this._links = new ArrayList<>();
    }

    public ApiMetadata(String version, String description) {
        this();
        this.version = version;
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Link> get_links() {
        return _links;
    }

    public void set_links(List<Link> _links) {
        this._links = _links;
    }

    public void addLink(Link link) {
        this._links.add(link);
    }
}
