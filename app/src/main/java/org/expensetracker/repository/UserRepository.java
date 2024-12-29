package org.expensetracker.repository;

import org.expensetracker.db.DataStore;
import org.expensetracker.db.MySQLConnection;
import org.expensetracker.model.User;

public final class UserRepository extends DataStore<User> {

    public UserRepository(MySQLConnection msc) {
        super(msc);
    }

}
