package com.nexcommerce.user.repository;

import com.nexcommerce.user.model.Address;
import com.nexcommerce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 地址存储库接口
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * 根据用户查找所有地址
     *
     * @param user 用户
     * @return 地址列表
     */
    List<Address> findByUser(User user);

    /**
     * 查找用户的默认地址
     *
     * @param user 用户
     * @param isDefault 是否为默认地址
     * @return 地址对象
     */
    Optional<Address> findByUserAndIsDefault(User user, Boolean isDefault);

    /**
     * 查找用户的默认账单地址
     *
     * @param user 用户
     * @param isBilling 是否为账单地址
     * @return 地址对象
     */
    Optional<Address> findByUserAndIsBilling(User user, Boolean isBilling);

    /**
     * 查找用户的默认配送地址
     *
     * @param user 用户
     * @param isShipping 是否为配送地址
     * @return 地址对象
     */
    Optional<Address> findByUserAndIsShipping(User user, Boolean isShipping);
}
