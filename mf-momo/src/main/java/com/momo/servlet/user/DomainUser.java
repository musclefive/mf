package com.momo.servlet.user;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
public class DomainUser {

    private static String adminName = "jvap\\ccserveradmin.vwed";//username@domain
    private static String adminPassword = "Pass@word1";//password
    private static String ldapURL = "LDAP://10.167.8.215:389/";//ip:port
    private static String BaseDN = "OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com";

    private static LdapContext ctx = null;
    private static Hashtable env = null;
    private static Control[] connCtls = null;

    public static void main(String[] args) {

//        getUsersOld();

        getUsers();
//        searchLDAP();
//        getSpecificAttr();

//        getUserDN("Yang.Bai@VWFAWEDL.com.cn");

    }
    //connect LDAP server
    public static void LDAP_connect(){
//        LdapContext ctx = null;
//        Hashtable env = new Hashtable();
//        Control[] connCtls = null;
        env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapURL+BaseDN);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.SECURITY_CREDENTIALS, "");


        try{
            ctx = new InitialLdapContext(env, connCtls);
        }catch (AuthenticationException e){
            System.out.println("Authentication failed : " + e.toString());
        }catch (Exception e){
            System.out.println("Something wrong while authenticating:" + e.toString());
        }
    }

    public static String getUserDN(String email){
        String userDN = "";
        LDAP_connect();

        try{
//            SearchControls constraints = new SearchControls();
//            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
//            NamingEnumeration en = ctx.search(BaseDN, "(sn=Cui)", constraints);

            SearchControls ctls = new SearchControls();
            String filter = "(&(sn=Cui)(mail=*)";
//            String filter = "(&(&(objectCategory=person)(objectClass=user))(sAMAccountName=cui))";
            NamingEnumeration en = ctx.search(BaseDN, filter, ctls);

//            SearchControls ctls = new SearchControls();
//            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
////            ctls.setReturningAttributes(attrIDs);
//            String filter = "(&(sn=Cui)(mail=*))";
//
//            NamingEnumeration en = ctx.search(BaseDN, filter, ctls);



            if(en == null){
                System.out.println("Have no NamingEnumeration");
            }
            if(!en.hasMoreElements()){
                System.out.println("Have no element");
            }
            while(en != null && en.hasMoreElements()){
                Object obj = en.nextElement();
                if(obj instanceof  SearchResult){
                    SearchResult si = (SearchResult) obj;
                    userDN += si.getName();
                    userDN += "," + BaseDN;
                }
                else{
                    System.out.println(obj);
                }
                System.out.println();
            }
        }catch (Exception e){
            System.out.println("Exception in search():" + e);
        }
        return userDN;
    }

    public static boolean authenricate(String ID, String password){
        boolean validate = false;
        String userDN = getUserDN(ID);
        try{
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, userDN);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
            ctx.reconnect(connCtls);
            System.out.println(userDN + "is authenticated(authenticate failed)");
            validate = true;
        }catch (AuthenticationException e){
            System.out.println(userDN + "is not authenticated(authenticate success)");
            System.out.println(e.toString());
            validate = false;
        }catch (NamingException e){
            System.out.println(userDN + " is not authenticated");
            validate = false;
        }
        return validate;
    }

    public static void searchLDAP(){
        Hashtable env = new Hashtable();


        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);

        env.put(Context.PROVIDER_URL, ldapURL);

        try {

            //第一种方式
            //ingore attribute name case
            Attributes matchAttrs = new BasicAttributes(true);
            //搜索的Item必须 sn=Cui mail 不限的条目
            matchAttrs.put(new BasicAttribute("sAMAccountName", "chao"));
            matchAttrs.put(new BasicAttribute("mail", "chao.cui@vwfawedl.com.cn"));
            String[] attrIDs = {"title", "sAMAccountName" , "department"};
            DirContext ctx = new InitialDirContext(env);
//            NamingEnumeration answer = ctx.search(BaseDN, matchAttrs, attrIDs);

            //第二种方式 sn=Cui 且mail不限
            SearchControls ctls = new SearchControls();
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            ctls.setReturningAttributes(attrIDs);
            String filter = "(&(sAMAccountName=chao.cui.vwed)(mail=*))";

            NamingEnumeration answer = ctx.search(BaseDN, filter, ctls);

            while (answer.hasMore()){
                SearchResult sr = (SearchResult) answer.next();
//                System.out.println(">>>>" + sr.getName() + ">>>>" + sr.getAttributes());
                System.out.println(">>>>" + sr.getName() + " " + sr.getAttributes());
            }

        } catch (AuthenticationException e) {


        } catch (NamingException e) {

            System.err.println("E:"+ e);

        }


    }

    public static void getSpecificAttr(){
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);

        try {

//            Context ctx = new InitialContext(env);
            DirContext ctx = new InitialDirContext(env);
//            String[] attrIDs = {"sn", "distinguishedName", "sAMAccountName", "mail"};
            Attributes answer = ctx.getAttributes("CN=Cui\\, Chao (VWEDII TL),OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com");
            NamingEnumeration ae = answer.getAll();

            int count = 0;
            while (ae.hasMore()) {
                count  = count + 1;
                Attribute attr = (Attribute) ae.next();
                System.out.print("Attribute:" + attr.getID());
                if("objectGUID".equals(attr.getID())){
                    String st = getGUID(attr.get().toString().getBytes());
                    System.out.println("ST:" + st);
                }


                NamingEnumeration e = attr.getAll();
                while (e.hasMore()){
                    System.out.println("   Value:" + e.next());
                }

            }

        } catch (AuthenticationException e) {


        } catch (NamingException e) {

            System.err.println("E:"+ e);

        }

    }
    /*System print out a list of insert SQLs */
    public static void getUsers(){
        Hashtable env = new Hashtable();

        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);
        String sn;
        String distinguishedName;
        String sAMAccountName;
        String mail;
        String title;
        String telephoneNumber;
        String whenCreated;
        String whenChanged;
        String proxyAddress;
        String physicalDeliveryOfficeName;
        String name;
        String mailNickname;
        String givenName;
        String cn;
        String company;
        String userAccountControl;
        String msExchUserCulture;
        String department;
        String displayName;
        String description;
        String userPrincipalName;
        String objectGUID;
        String user;
        try {

            DirContext ctx = new InitialDirContext(env);
            NamingEnumeration list = ctx.list(BaseDN);
            String[] attrIDs = {"sn", "distinguishedName", "sAMAccountName", "mail","title",
                    "telephoneNumber", "whenCreated", "whenChanged", "proxyAddress", "physicalDeliveryOfficeName",
                    "name", "mailNickname", "givenName", "cn", "company", "userAccountControl", "msExchUserCulture",
                    "department", "displayName", "description", "userPrincipalName","objectGUID"};
            String insert = "insert into DomainUser(sn, distinguishedName,sAMAccountName,mail," +
                    "title,telephoneNumber,whenCreated,whenChanged,proxyAddress,physicalDeliveryOfficeName," +
                    "name,mailNickname,givenName,cn,company,userAccountControl,msExchUserCulture," +
                    "department,displayName,description,userPrincipalName,objectGUID) values ";
//            NamingEnumeration bindings = ctx.listBindings("OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com");
            String insertValue;
            int count = 0;
            while (list.hasMore()) {
                count  = count + 1;
                NameClassPair nc = (NameClassPair)list.next();
                user = nc.getName() + "," +BaseDN;
                Attributes answer = ctx.getAttributes(user, attrIDs);
                NamingEnumeration ae = answer.getAll();
//                System.out.println(nc.getName());
                Map mapTmp = new HashMap();
                while (ae.hasMore()) {
                    Attribute attr = (Attribute) ae.next();

//                    System.out.print("Attribute:" + attr.getID());
//                    System.out.println(" value:" + attr.get(0));
                    String key = attr.getID();
                    String value = attr.get(0).toString();
                    mapTmp.put(key, value);
                }
                sn = mapTmp.containsKey("sn") ? mapTmp.get("sn").toString() : "";
                distinguishedName = mapTmp.containsKey("distinguishedName") ? mapTmp.get("distinguishedName").toString() : "";
                sAMAccountName = mapTmp.containsKey("sAMAccountName") ? mapTmp.get("sAMAccountName").toString() : "";
                mail = mapTmp.containsKey("mail") ? mapTmp.get("mail").toString() : "";
                title = mapTmp.containsKey("title") ? mapTmp.get("title").toString() : "";
                telephoneNumber = mapTmp.containsKey("telephoneNumber") ? mapTmp.get("telephoneNumber").toString() : "";
                whenCreated = mapTmp.containsKey("whenCreated") ? mapTmp.get("whenCreated").toString() : "";
                whenChanged = mapTmp.containsKey("whenChanged") ? mapTmp.get("whenChanged").toString() : "";
                proxyAddress = mapTmp.containsKey("proxyAddress") ? mapTmp.get("proxyAddress").toString() : "";
                physicalDeliveryOfficeName = mapTmp.containsKey("physicalDeliveryOfficeName") ? mapTmp.get("physicalDeliveryOfficeName").toString() : "";
                name = mapTmp.containsKey("name") ? mapTmp.get("name").toString() : "";
                mailNickname = mapTmp.containsKey("mailNickname") ? mapTmp.get("mailNickname").toString() : "";
                givenName = mapTmp.containsKey("givenName") ? mapTmp.get("givenName").toString() : "";
                cn = mapTmp.containsKey("cn") ? mapTmp.get("cn").toString() : "";
                company = mapTmp.containsKey("company") ? mapTmp.get("company").toString() : "";
                userAccountControl = mapTmp.containsKey("userAccountControl") ? mapTmp.get("userAccountControl").toString() : "";
                msExchUserCulture = mapTmp.containsKey("msExchUserCulture") ? mapTmp.get("msExchUserCulture").toString() : "";
                department = mapTmp.containsKey("department") ? mapTmp.get("department").toString() : "";
                displayName = mapTmp.containsKey("displayName") ? mapTmp.get("displayName").toString() : "";
                description = mapTmp.containsKey("description") ? mapTmp.get("description").toString() : "";
                userPrincipalName = mapTmp.containsKey("userPrincipalName") ? mapTmp.get("userPrincipalName").toString() : "";
                objectGUID = mapTmp.containsKey("objectGUID") ? getGUID(mapTmp.get("objectGUID").toString().getBytes()).toString() : "";

                insertValue = "'" + sn + "'," +
                        "'" + distinguishedName + "'," +
                        "'" + sAMAccountName + "'," +
                        "'" + mail + "'," +
                        "'" + title+ "'," +
                        "'" + telephoneNumber + "'," +
                        "'" + whenCreated + "'," +
                        "'" + whenChanged + "'," +
                        "'" + proxyAddress + "'," +
                        "'" + physicalDeliveryOfficeName + "'," +
                        "'" + name + "'," +
                        "'" + mailNickname + "'," +
                        "'" + givenName + "'," +
                        "'" + cn + "'," +
                        "'" + company + "'," +
                        "'" + userAccountControl + "'," +
                        "'" + msExchUserCulture + "'," +
                        "'" + department + "'," +
                        "'" + displayName + "'," +
                        "'" + description + "'," +
                        "'" + userPrincipalName + "'," +
                        "'" + objectGUID + "'";

                System.out.println(insert + " ( " + insertValue +" );");
            }

        } catch (AuthenticationException e) {

            // attempt to reacquire the authentication information

        } catch (NamingException e) {

            // Handle the error

            System.err.println("E:"+ e);

        }

        System.out.println(adminName);
    }

    public static void getUsersOld(){
        Hashtable env = new Hashtable();
        String searchBase = "OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com";
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);

        try {

            LdapContext ctx = new InitialLdapContext(env, null); //创建上下文
            //LDAP 搜索过滤器
            String searchFilter = "objectClass=User";
            //搜索控制器
            SearchControls searchCtls = new SearchControls();
            //设置搜索的范围
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            //定制返回属性
            String returnAttrs[] = {"sn", "distinguishedName", "sAMAccountName", "mail","title",
                    "telephoneNumber", "whenCreated", "whenChanged", "proxyAddress", "physicalDeliveryOfficeName",
                    "name", "mailNickname", "givenName", "cn", "company", "userAccountControl", "msExchUserCulture",
                    "department", "displayName", "description", "userPrincipalName", "ObjectSID", "ObjectGUID"};
            searchCtls.setReturningAttributes(returnAttrs);
            //根据设置的域节点,过滤器类,搜索控制器范围搜素LDAP得到结果
            NamingEnumeration answer = ctx.search(searchBase, searchFilter, searchCtls);
            int totalResult = 0;
            int rows = 0;

            while (answer.hasMoreElements()){
                SearchResult sr = (SearchResult) answer.next();
                System.out.println(++rows + "******************************");
                String dn = sr.getName();
                String match = dn.split("CN=")[1].split(",")[0];
                System.out.println(match);

                //获取属性集
                Attributes attrs = sr.getAttributes();
                NamingEnumeration ne = attrs.getAll();
                while ( ne.hasMore() ){
                    //获得具体某一个属性
                    Attribute attr = (Attribute)ne.next();
                    System.out.println("AttributeID=" + attr.getID().toString());
                    if("objectGUID".equals(attr.getID())){
                        String st = getGUID(attr.get().toString().getBytes());
                        System.out.println("ST:" + st);
                    }

                }

            }

        } catch (AuthenticationException e) {

            // attempt to reacquire the authentication information

        } catch (NamingException e) {

            // Handle the error

            System.err.println("E:"+ e);

        }

        System.out.println(adminName);
    }


    private static String getGUID(byte[] inArr) {
        StringBuffer guid = new StringBuffer();
        for (int i = 0; i < inArr.length; i++) {
            StringBuffer dblByte = new StringBuffer(
                    Integer.toHexString(inArr[i] & 0xff));
            if (dblByte.length() == 1) {
                guid.append("0");
            }
            guid.append(dblByte);
        }
        return guid.toString();
    }


    /*
    *
    * insert into DomainUser(sn, distinguishedName,sAMAccountName,mail,title,telephoneNumber,whenCreated,whenChanged,proxyAddress,physicalDeliveryOfficeName,name,mailNickname,givenName,cn,company,userAccountControl,msExchUserCulture,department,displayName,description,userPrincipalName,objectGUID) values  ( 'Cao','CN=Cao\, Wangwei (VWED TS),OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com','Wangwei.Cao','Wangwei.Cao@VWFAWEDL.com.cn','DL Allgemein Planung Supervisor & CC project Manager','+86-411-39210 058','20070425031045.0Z','20160225000536.0Z','','VWED TS','Cao, Wangwei (VWED TS)','Wangwei.Cao','Wangwei','Cao, Wangwei (VWED TS)','VW FAW Engine (Dalian) Co., Ltd. Changchun Branch','512','zh-CN','VWED TS','Cao, Wangwei (VWED TS)','DL Allgemein Planung Supervisor CC project Manager','Wangwei.Cao@jvap.com','3f373f3f7104413f3f0f3f04263f' );

insert into DomainUser(sn, distinguishedName,sAMAccountName,mail,title,telephoneNumber,whenCreated,whenChanged,proxyAddress,physicalDeliveryOfficeName,name,mailNickname,givenName,cn,company,userAccountControl,msExchUserCulture,department,displayName,description,userPrincipalName,objectGUID) values  ( 'Che','CN=Che\, Wenjiao (VWED PM),OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com','wenjiao.che.VWED','Wenjiao.Che@VWFAWEDL.com.cn','Projekt Supervisor (Sonder- und Kleinprojekte)','+86-411-3921 0416','20090821032419.0Z','20160219183801.0Z','','VWED AAC 3.E.','Che, Wenjiao (VWED PM)','wenjiao.che.VWED','Wenjiao','Che, Wenjiao (VWED PM)','VW FAW Engine (Dalian) Co., Ltd.','512','zh-CN','VWED PM','Che, Wenjiao (VWED PM)','Projekt Supervisor (Sonder- und Kleinprojekte), VWED PM','wenjiao.che.VWED@jvap.com','3f3f6a1f33193f463f3f7f3f46113f' );

insert into DomainUser(sn, distinguishedName,sAMAccountName,mail,title,telephoneNumber,whenCreated,whenChanged,proxyAddress,physicalDeliveryOfficeName,name,mailNickname,givenName,cn,company,userAccountControl,msExchUserCulture,department,displayName,description,userPrincipalName,objectGUID) values  ( 'Liu','CN=Liu\, Bin (VWED),OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com','Bin.Liu.VWED','Bin.Liu@VWFAWEDL.com.cn','','+86-411-39210061','20140224035708.0Z','20160228105651.0Z','','CH','Liu, Bin (VWED)','Bin.Liu.VWED','Bin','Liu, Bin (VWED)','Volkswagen Engine Dalian','512','','VWED CH','Liu, Bin (VWED)','Chairman of the Labor Union, Deputy Secretary of CPC Party branch and Secretary of the Commission for Discipline Inspection','Bin.Liu.VWED@jvap.com','6202533f2c7d0b403f3f5d7b3f3f2755' );

insert into DomainUser(sn, distinguishedName,sAMAccountName,mail,title,telephoneNumber,whenCreated,whenChanged,proxyAddress,physicalDeliveryOfficeName,name,mailNickname,givenName,cn,company,userAccountControl,msExchUserCulture,department,displayName,description,userPrincipalName,objectGUID) values  ( 'Wang','CN=Wang\, Yuan (VWED AAC),OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com','Yuan.Wang.VWED','Yuan.Wang2@VWFAWEDL.com.cn','Remanufacturing&EOP Spare Parts Disposition Engineer','+86-411-39260614','20141029080509.0Z','20160228235639.0Z','','AAC','Wang, Yuan (VWED AAC)','Yuan.Wang.VWED','Yuan','Wang, Yuan (VWED AAC)','VW FAW Engine (Dalian) Co., Ltd.','512','','VWED AAC','Wang, Yuan (VWED AAC)','Remanufacturing&EOP Spare Parts Disposition Engineer','Yuan.Wang.VWED@jvap.com','0e3f3f153f7978493f603f0d3f613f' );

insert into DomainUser(sn, distinguishedName,sAMAccountName,mail,title,telephoneNumber,whenCreated,whenChanged,proxyAddress,physicalDeliveryOfficeName,name,mailNickname,givenName,cn,company,userAccountControl,msExchUserCulture,department,displayName,description,userPrincipalName,objectGUID) values  ( 'Yang','CN=Yang\, Guang (VWED AAC),OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com','Guang.Yang.VWED','Guang.Yang@VWFAWEDL.com.cn','','+86-411- 39260657','20140526023115.0Z','20160225001622.0Z','','AAC','Yang, Guang (VWED AAC)','Guang.Yang.VWED','Guang','Yang, Guang (VWED AAC)','VW FAW Engine (Dalian) Co., Ltd. Montage Coordination Ing.','512','','VWED AAC','Yang, Guang (VWED AAC)','Montage Coordination Ing.','Guang.Yang.VWED@jvap.com','3f06483f3f3f3f423f373f3f563f23' );

insert into DomainUser(sn, distinguishedName,sAMAccountName,mail,title,telephoneNumber,whenCreated,whenChanged,proxyAddress,physicalDeliveryOfficeName,name,mailNickname,givenName,cn,company,userAccountControl,msExchUserCulture,department,displayName,description,userPrincipalName,objectGUID) values  ( 'Zhang','CN=Zhang\, Wenbin (VWED AAC),OU=Users,OU=VWED,OU=Mail Hosting,OU=Domain Users,DC=jvap,DC=com','wenbin.zhang.VWED','Wenbin.Zhang@VWFAWEDL.com.cn','Produktion Supervisor in der AAC','+86 411 39260655','20090901064256.0Z','20160225001149.0Z','','AAC','Zhang, Wenbin (VWED AAC)','wenbin.zhang.VWED','Wenbin','Zhang, Wenbin (VWED AAC)','VW FAW Engine (Dalian) Co., Ltd.','512','','VWED AAC','Zhang, Wenbin (VWED AAC)','Produktion Supervisor in der Anlauf & Aufbereitungs- Center','wenbin.zhang.VWED@jvap.com','3f3f34043f5b3f423f3f3f3f3f4b3f' );
    *
    *
    * */
}
