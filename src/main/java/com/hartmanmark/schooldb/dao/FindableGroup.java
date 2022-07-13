package com.hartmanmark.schooldb.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.hartmanmark.schooldb.model.Group;

public interface FindableGroup {

    public List<Group> findGroups() throws ClassNotFoundException, IOException, NullPointerException, SQLException;
}
