# ForumBackAPI

Bem-vindo ao **ForumBackAPI**, a API backend para o aplicativo de fórum, desenvolvida para gerenciar operações de usuários, tópicos e comentários. Este projeto foi construído utilizando Java com o framework Spring Boot.

## 🚀 Funcionalidades

- **Gerenciamento de Usuários**: CRUD completo para usuários do fórum.
- **Gerenciamento de Tópicos**: Permite a criação, leitura, atualização e exclusão de tópicos de discussão.
- **Gerenciamento de Comentários**: Suporta operações CRUD para comentários em tópicos.
- **Autenticação e Autorização**: Implementação de segurança para acesso às funcionalidades da API.

## 📂 Estrutura do Projeto
ForumBackAPI/ ├── .mvn/ │ └── wrapper/ ├── src/ │ ├── main/ │ │ ├── java/ │ │ │ └── com/ │ │ │ └── forumbackapi/ │ │ │ └── ... │ │ └── resources/ │ │ ├── application.properties │ │ └── ... │ └── test/ │ └── java/ │ └── com/ │ └── forumbackapi/ │ └── ... ├── .gitattributes ├── .gitignore ├── mvnw ├── mvnw.cmd ├── pom.xml └── README.md


- **.mvn/**: Arquivos do Maven Wrapper para facilitar a construção do projeto.
- **src/**: Diretório principal do código-fonte.
  - **main/**: Contém o código-fonte da aplicação.
    - **java/**: Código Java organizado por pacotes.
    - **resources/**: Arquivos de configuração e recursos estáticos.
  - **test/**: Código de testes unitários e de integração.
- **pom.xml**: Arquivo de configuração do Maven com as dependências e plugins do projeto.

## 🛠️ Configuração do Projeto

Siga os passos abaixo para configurar e executar o projeto localmente:

### 1. Pré-requisitos

- **Java 11+**: Certifique-se de ter o [Java Development Kit (JDK) 11 ou superior](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) instalado.
- **Maven 3.6+**: [Apache Maven](https://maven.apache.org/download.cgi) para gerenciamento de dependências e construção do projeto.

### 2. Clone o Repositório

```bash
git clone https://github.com/GustavoHenriqueDEV/ForumBackAPI.git
cd ForumBackAPI
3. Configure o Banco de Dados
Configure as credenciais e detalhes de conexão no arquivo src/main/resources/application.properties.
4. Compile e Execute o Projeto
bash
Copiar código
# Compile o projeto
mvn clean install

# Execute a aplicação
mvn spring-boot:run
A aplicação estará disponível em http://localhost:8080.

✏️ Personalização
Para personalizar as configurações do projeto, edite o arquivo application.properties conforme necessário. Consulte a Documentação do Spring Boot para mais detalhes.

🤝 Contribuição
Contribuições são bem-vindas! Siga as etapas abaixo para contribuir:

bash
Copiar código
# 1. Faça um fork do projeto.
git fork

# 2. Crie um branch para sua feature.
git checkout -b minha-feature

# 3. Faça commit das alterações.
git commit -m 'Adicionei minha feature'

# 4. Envie para o branch.
git push origin minha-feature

# 5. Abra um Pull Request.
# Após a revisão, sua contribuição poderá ser incluída no projeto.
📄 Licença
Este projeto está licenciado sob a licença MIT. Sinta-se à vontade para usá-lo e modificá-lo conforme necessário.

📞 Contato
Para mais informações ou suporte, visite o repositório do projeto no GitHub: ForumBackAPI.
