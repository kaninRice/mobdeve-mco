package com.example.s17.escopete.stevenerrol.arpeggeo.tag.data;

import java.util.ArrayList;

public interface TagRepository {
    ArrayList<Tag> getAllTags();
    Tag getTagByIndex(int index);
    String getTagNameByIndex(int index);
    String getTagColor(String name);
    TextColor getTagTextColor(String name);
}
