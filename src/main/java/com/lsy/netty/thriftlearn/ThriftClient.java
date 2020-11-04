package com.lsy.netty.thriftlearn;

import com.lsy.netty.thrift.generated.Person;
import com.lsy.netty.thrift.generated.PersonService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author lsy
 * @date 2020/11/4 18:35
 */
public class ThriftClient {
    public static void main(String[] args) {
        TTransport transport = new TFastFramedTransport(new TSocket("localhost", 8899), 600);
        TProtocol protocol = new TCompactProtocol(transport);
        PersonService.Client client = new PersonService.Client(protocol);

        try {

            transport.open();
            Person person = client.findPerson("张三");

            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            Person p = new Person();
            p.setUsername("李四");
            p.setAge(35);
            p.setMarried(true);

            client.savePersion(p);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            transport.close();
        }
    }
}
