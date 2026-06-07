package searchengineapi.service;

import java.util.HashMap;
import java.util.Map;

import searchengineapi.model.DocumentMetadata;

public class DocumentMetadataStore {
    private Map<String,DocumentMetadata> metaDataMap = new HashMap<>();

    public void addMetaData(DocumentMetadata metadata)
    {
        metaDataMap.put(metadata.getUrl(),metadata);
    }

    public DocumentMetadata getMetadata(String url)
    {
        return metaDataMap.get(url);
    }
}
