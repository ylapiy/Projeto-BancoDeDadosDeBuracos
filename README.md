Para o back end funcionar normalmente o projeto deve ser uma hospedado em servidor com suporte para Apache Tomcat;
Site com apenas funcionalidades do front end: [https://foioguto.github.io/buraco-front/](https://foioguto.github.io/buraco-front/index.html)

Para rodar o site em um servidor local apasho tomcat ---

1.baixar o .war da aplicação
2.configurar a extesão commmuniti server concetor -> vai aparecer uma nova opção servers no canto inferior esquerdo (incusive é necessario o JDK pra funcionar)
3.adicicionar um novo servidor tomcat 10.X.X
4.levar o .war ate a pasta C:\Users\teecno\.rsp\redhat-community-server-connector\runtimes\installations\tomcat-10.1.23\apache-tomcat-10.1.23\webapps
5.iniciar o servidor local (so apertar com o botão direito no servidor tomcat) 
6.usar o camiho do site http://localhost:8080/Projeot-SQLdeBuracos-0.0.1-SNAPSHOT/app/home/index.html
7.quando o formulario for enviado talvez ele pessa pra logar com a conta do google( infelismente eu não tive como testar com qualquer outra que não fosse a minha) alguma da instituição icev
8. o JDBC vai entrar em ação e mandar o formulario para o banco de dados e quando for na pagina de serviços vai printar toda a tabela 
