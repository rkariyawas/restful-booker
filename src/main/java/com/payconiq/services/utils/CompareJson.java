package com.payconiq.services.utils;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompareJson {

    private final JsonNode root;
    private final List<JsonPointer> pointers = new ArrayList<>();

    public CompareJson(JsonNode root) {
        this.root = Objects.requireNonNull(root);
        findPointers(this.root, "");
    }

    public boolean contains(CompareJson other) {
        //If number of pointers are different, JSON payloads are also different
        if (this.pointers.size() < other.pointers.size()) {
            return false;
        }
        // If pointers are not the same
        if (!this.pointers.containsAll(other.pointers)) {
            return false;
        }
        // check values
        return other.pointers
                .stream()
                .allMatch(p -> this.root.at(p).equals(other.root.at(p)));
    }

    private void findPointers(JsonNode node, String path) {
        if (node.isObject()) {
            node.fields().forEachRemaining(entry -> findPointers(entry.getValue(), path + "/" + entry.getKey()));
        } else if (node.isArray()) {
            final int size = node.size();
            for (int i = 0; i < size; i++) {
                findPointers(node.get(i), path + "/" + i);
            }
        } else {
            pointers.add(JsonPointer.compile(path));
        }
    }

    @Override
    public String toString() {
        return pointers.toString();
    }
}
