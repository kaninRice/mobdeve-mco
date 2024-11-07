package com.example.s17.escopete.stevenerrol.arpeggeo.tag.data;

import java.util.ArrayList;

public interface TagRepository {
    ArrayList<Tag> getAllTags();
    Tag getTagByIndex(int index);
    String getTagName(Tag tag);
    String getTagColor(Tag tag);
    TextColor getTagTextColor(Tag tag);
}
