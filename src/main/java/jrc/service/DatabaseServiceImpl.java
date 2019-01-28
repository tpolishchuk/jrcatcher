package jrc.service;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService{

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void truncateAllTables() {
        List<String> tableNames = new ArrayList<>();
        Session session = entityManager.unwrap(Session.class);

        session.getSessionFactory()
               .getMetamodel()
               .getEntities()
               .forEach(entityType -> {
                   String tableName = entityType.getBindableJavaType()
                                                .getAnnotation(Table.class)
                                                .name();
                   tableNames.add(tableName);
               });

        entityManager.flush();

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0")
                     .executeUpdate();

        tableNames.forEach(tableName -> {
                               entityManager.createNativeQuery("TRUNCATE TABLE " + tableName)
                                            .executeUpdate();
                           });

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1")
                     .executeUpdate();
    }
}
