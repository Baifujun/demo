package com.baixs.demo;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

@SpringBootTest
public class MailTest {

    @Test
    public void ReciveIMAPmail() throws MessagingException, IOException {
        // 准备连接服务器的会话信息
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", "imap");
        props.setProperty("mail.debug", "true");
        props.setProperty("mail.imap.host", "imap.qq.com");
        props.setProperty("mail.imap.port", "993");
        props.setProperty("mail.imap.ssl.enable", "true");

        // 创建Session实例对象
        Session session = Session.getInstance(props);

        // 创建IMAP协议的Store对象
        Store store = session.getStore("imap");

        // 连接邮件服务器
        store.connect("951398817@qq.com", "vhkldnhdlklkbfeb");

        // 获得收件箱
        Folder folder = store.getFolder("INBOX");
        // 以读写模式打开收件箱
        folder.open(Folder.READ_WRITE);

        //folder.search()

        // 获得收件箱的邮件列表
        Message[] messages = folder.getMessages();

        // 打印不同状态的邮件数量
        System.out.println("收件箱中共" + messages.length + "封邮件!");
        System.out.println("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
        System.out.println("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
        System.out.println("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");

        System.out.println("------------------------开始解析邮件----------------------------------");
        int total = folder.getMessageCount();
        System.out.println("-----------------您的邮箱共有邮件：" + total + " 封--------------");
        // 得到收件箱文件夹信息，获取邮件列表
        Message[] msgs = folder.getMessages();
        System.out.println("\t收件箱的总邮件数：" + msgs.length);
        for (int i = 0; i < total; i++) {
            Message a = msgs[i];
            if (i == total - 1) {
                msgs[i].writeTo(new FileOutputStream(new File("D:/test.eml")));
            }
            //   获取邮箱邮件名字及时间
            System.out.println(a.getReplyTo());
            System.out.println("==============");
            System.out.println(a.getSubject() + "   接收时间：" + a.getReceivedDate().toLocaleString() + "  contentType()" + a.getContentType());
        }
        System.out.println("\t未读邮件数：" + folder.getUnreadMessageCount());
        System.out.println("\t新邮件数：" + folder.getNewMessageCount());
        System.out.println("----------------End------------------");

        // 关闭资源
        folder.close(false);
        store.close();
    }
}
