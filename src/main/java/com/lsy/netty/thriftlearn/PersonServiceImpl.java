package com.lsy.netty.thriftlearn;

import com.lsy.netty.thrift.generated.DataException;
import com.lsy.netty.thrift.generated.Person;
import com.lsy.netty.thrift.generated.PersonService;
import org.apache.thrift.TException;

/**
 * @author lsy
 * @date 2020/11/4 18:10
 */
public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person findPerson(String username) throws DataException, TException {
        System.out.println("Got client param：" + username);

        Person person = new Person();
        person.setUsername(username)
                .setAge(26)
                .setMarried(true);

        return person;
    }

    @Override
    public void savePersion(Person person) throws DataException, TException {
        String str = "姓名：%s，年龄：%s，是否已婚：%s";
        String params = String.format(str, person.getUsername(), person.getAge(), person.isMarried());

        System.out.println(params);
    }
}
