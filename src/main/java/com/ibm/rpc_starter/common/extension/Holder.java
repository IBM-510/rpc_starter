package com.ibm.rpc_starter.common.extension;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: ibm_5100
 * @Date: 2023/06/01/13:43
 * @Description:
 */
public class Holder<T>{
    //在多线程环境下也能保证线程间的可见性。
    // 这样可以确保一个线程对value的修改对其他线程是可见的，
    // 避免了线程之间的数据不一致性问题。
    //保证线程安全性，并减少对锁的需求
    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
