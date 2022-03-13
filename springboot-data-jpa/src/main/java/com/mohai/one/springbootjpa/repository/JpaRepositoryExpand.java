package com.mohai.one.springbootjpa.repository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class JpaRepositoryExpand <T, ID> extends SimpleJpaRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;

    @Autowired
    public JpaRepositoryExpand(
            JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        // 获取ID
        ID entityId = (ID) this.entityInformation.getId(entity);
        T managedEntity;
     //   T mergedEntity;
        if (entityId == null) {
            em.persist(entity);
           // mergedEntity = entity;
        } else {
            managedEntity = this.findById(entityId).get();
            if (managedEntity == null) {
                em.persist(entity);
               // mergedEntity = entity;
            } else {
                BeanUtils.copyProperties(entity, managedEntity, getNullProperties(entity));
                em.merge(managedEntity);
              //  mergedEntity = managedEntity;
            }
        }
        return entity;
    }

    /**
     * 判断获取对象空属性
     * @param src
     * @return
     */
    private static String[] getNullProperties(Object src) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> nullProperties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = srcBean.getPropertyValue(propertyName);
            if (StringUtils.isEmpty(propertyValue)) {
                srcBean.setPropertyValue(propertyName, null);
                nullProperties.add(propertyName);
            }
        }
        return nullProperties.toArray(new String[0]);
    }

    public static void copyProperties(Object source,Object target){
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }

    public static void main(String[] args) {
        Set<String> str = new HashSet<>();
        str.add("aaa");
        str.add("bbb");
        String[] strArr = str.toArray(new String[0]);
        System.out.println(strArr);
        System.out.println(strArr.length);
        System.out.println(strArr[0]);
    }
}