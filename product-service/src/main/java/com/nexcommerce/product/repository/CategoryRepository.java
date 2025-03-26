package com.nexcommerce.product.repository;

import com.nexcommerce.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 类别存储库接口
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 根据类别名称查找类别
     *
     * @param name 类别名称
     * @return 类别对象
     */
    Optional<Category> findByName(String name);

    /**
     * 根据类别slug查找类别
     *
     * @param slug 类别slug
     * @return 类别对象
     */
    Optional<Category> findBySlug(String slug);

    /**
     * 查找所有顶级类别（没有父类别）
     *
     * @return 顶级类别列表
     */
    List<Category> findByParentIsNull();

    /**
     * 根据父类别ID查找子类别
     *
     * @param parentId 父类别ID
     * @return 子类别列表
     */
    List<Category> findByParentId(Long parentId);

    /**
     * 查找激活状态的类别
     *
     * @return 激活状态的类别列表
     */
    List<Category> findByIsActiveTrue();

    /**
     * 查找包含指定关键字的类别
     *
     * @param keyword 关键字
     * @return 类别列表
     */
    List<Category> findByNameContaining(String keyword);

    /**
     * 根据排序顺序查找类别
     *
     * @return 排序后的类别列表
     */
    List<Category> findAllByOrderBySortOrderAsc();

    /**
     * 获取类别树（递归查询）
     * 
     * @param parentId 父类别ID
     * @return 类别列表（包含子类别）
     */
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children WHERE c.id = :parentId")
    Optional<Category> findWithChildrenById(@Param("parentId") Long parentId);
}
