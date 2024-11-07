# Exam questions backend exam 2024

## Where: Lokale CL-1.C.7          09.00 - 13.00

## What is a REST API, and why is it commonly used in web development?

  - API conforming to the REST architecture. Convenient, uniform way to
      a resource available.

#### What REST IS NOT about

    Pretty URIs like

    ```
    /customer/12/profile
    ```

    Having to use all of the HTTP methods like GET, POST, PUT, DELETE

#### What REST IS about

  - discoverability of resources
  - resources that are addressable
  - loose coupling between client and server
  - making use of existing standards: like HTML, HTTP, URI and standardized media types
  - long living systems that can evolve independently over time

#### Checklist: Is my API RESTful?

If you recognize these things in your application, there's a high chance, that REST constraints are violated.

  - Your clients are performing string concatenation to build resource URLs.
    => REST clients should have no knowledge of resource URLs other than the
    initial one. This demostrates a high coupling between client and server.
  - Server responses don't contain links to other resources => Violates
    hypertext/hypermedia constraint. In the inital response clients should
    receive links to other available resources.
  - Client says, give me the next page: => Violates stateless constraint. To
    fulfill that the server would have to keep track of what the "next page"
    actually is. The correct RESTful way of doing that would be if the server
    returns the current page along with a link to the next one, which can then
    be used by the client to retrieve the next page.

#### Constraints

  1. uniform interface
  2. client-server
  3. stateless
  4. cacheable
  5. layered system
  6. code on demand (opt)

####  Constraints in detail

  - A REST API should not be dependent on any single communication protocol,
    though its successful mapping to a given protocol may be dependent on the
    availability of metadata, choice of methods, etc. In general, any protocol
    element that uses a URI for identification must allow any URI scheme to be
    used for the sake of that identification. [Failure here implies that
    identification is not separated from interaction.]
  - A REST API should not contain any changes to the communication protocols
    aside from filling-out or fixing the details of underspecified bits of
    standard protocols, such as HTTP’s PATCH method or Link header field.
    Workarounds for broken implementations (such as those browsers stupid
    enough to believe that HTML defines HTTP’s method set) should be defined
    separately, or at least in appendices, with an expectation that the
    workaround will eventually be obsolete. [Failure here implies that the
    resource interfaces are object-specific, not generic.]
  - A REST API should spend almost all of its descriptive effort in defining
    the media type(s) used for representing resources and driving application
    state, or in defining extended relation names and/or hypertext-enabled
    mark-up for existing standard media types. Any effort spent describing what
    methods to use on what URIs of interest should be entirely defined within
    the scope of the processing rules for a media type (and, in most cases,
    already defined by existing media types). [Failure here implies that
    out-of-band information is driving interaction instead of hypertext.]
  - A REST API must not define fixed resource names or hierarchies (an obvious
    coupling of client and server). Servers must have the freedom to control
    their own namespace. Instead, allow servers to instruct clients on how to
    construct appropriate URIs, such as is done in HTML forms and URI
    templates, by defining those instructions within media types and link
    relations. [Failure here implies that clients are assuming a resource
    structure due to out-of band information, such as a domain-specific
    standard, which is the data-oriented equivalent to RPC’s functional
    coupling].
  - A REST API should never have “typed” resources that are significant to the
    client. Specification authors may use resource types for describing server
    implementation behind the interface, but those types must be irrelevant and
    invisible to the client. The only types that are significant to a client
    are the current representation’s media type and standardized relation
    names. [ditto]
  - A REST API should be entered with no prior knowledge beyond the initial URI
    (bookmark) and set of standardized media types that are appropriate for the
    intended audience (i.e., expected to be understood by any client that might
    use the API). From that point on, all application state transitions must be
    driven by client selection of server-provided choices that are present in
    the received representations or implied by the user’s manipulation of those
    representations. The transitions may be determined (or limited by) the
    client’s knowledge of media types and resource communication mechanisms,
    both of which may be improved on-the-fly (e.g., code-on-demand). [Failure
    here implies that out-of-band information is driving interaction instead of
    hypertext.]

### What does ReST stand for?

  - Representational State Transfer.

### What are the main principles of RESTful architecture?

  - Client-server architecture.
  - Stateless.
  - Cacheable data (streamlining, efficiency, performance).
  - Uniform interface between components; can enable hyperlinks for example.

### What are semantic URLs, and how do they improve the readability of RESTful APIs?

  - "meaningful urls" eg. ```{{url}}/books/harrypotter/1```

## Explain the purpose of using DTO (Data Transfer Object) classes in a java application.

  - An object that carries data between processes in order to
      reduce the number of method calls.
  - Avoid N+1 problem where a client has to send additional N
    requests to retrieve an object like GET trip + attendants.

### Where are DTO classes typically used in the context of RESTful APIs?

  - transferring data to the client(s).
  - backend representation mapped to DTOs in controller/service layer and shipped
    to view.

### How can DTO classes help in decoupling the domain model from the API response?

  - allows system-designers to decide exact representation of internal
      data sent to clients/frontend.
  - clients/frontend wouldn't need to worry about internal logic,
      like representation of timestamps for example.

### Should DTO classes import domain model classes? Why or why not?

  - dto is a mapping of domain model to view, so yes, in parts and
      in any form preferred.

## What is the difference between HTTP GET, POST, PUT, and DELETE methods? Provide examples for each.

### Overview

| **Method**  | **Safe** | **Idempotent** | **Cacheable** |
|-------------|----------|----------------|---------------|
| **GET**     | Yes      | Yes            | Yes           |
| **HEAD**    | Yes      | Yes            | Yes           |
| **OPTIONS** | Yes      | Yes            | No            |
| **TRACE**   | Yes      | Yes            | No            |
| **PUT**     | No       | Yes            | No            |
| **DELETE**  | No       | Yes            | No            |
| **POST**    | No       | No             | Conditional*  |
| **PATCH**   | No       | No             | Conditional*  |
| **CONNECT** | No       | No             | No            |

 - GET: request resource
 - POST: send resource
 - PUT: update resource
 - DELETE: delete resource

### How are these HTTP methods typically used in RESTful API design?

  -

### What are the main differences between PUT and POST methods in terms of idempotency and safety?

  -

### How can the PATCH method be used to update resources in a RESTful API?

  - Allows partial json for example ```PATCH {{url}}/api/doctor/1 with body: { "name":
      "LOL" }```

### What is the purpose of the OPTIONS method in HTTP?

  - Query server for available endpoints for specific url or server
    (```OPTIONS *|<request-target>["?"<query>] HTTP/1.1```)

## What is error handling in REST APIs, and why is it important?

  - Catching various client- and server-side errors & responding appropriately;
    namely by correctly assigning http status codes in our example.

### How can HTTP status codes be used to communicate errors in a RESTful API?

  - ^

### What are some common error codes and their meanings in the context of RESTful APIs?

  - Client-side errors:
    + 400 (Bad Request)
  - Client-side errors:
    + 500 (Internal Server Error)

### How can custom error messages be returned in a RESTful API response?

  - As a field in a json for example; IE. a format which the client expects
    and knows how to deal with.

## Describe how JSON is typically used in RESTful APIs for data exchange.

  - Serializing objects like DTOs in our code for the purpose of data exchange.

### What are the advantages of using JSON over other data formats like XML?

  - json is more compact format for data transfer (think objects), whereas
    XML is more compact for more complicated documents like html.
  - XML is a markup language; it does that job better than json.

### How can JSON be serialized and deserialized in Java using libraries like Jackson?

  - using reflection, these libs can fill out fields in objects.

### What is the purpose of the Content-Type header in an HTTP request or response?

  - data needs to be interpreted, and it is not always possible to infer data type;
    EG. png, html, json, js etc.

## What is logging in the context of application development, and how can it improve error management and debugging?

  - logging is like printf-debugging on steroids.

### How can logging be implemented in a Java application using libraries like Logback?

  - format and error-levels can be adjusted in an xml config-file.

### What are the different log levels, and when should they be used in an application?

  - Trace, debug, info, warn, error.

### How can log messages be formatted to include relevant information like timestamps and log levels?

```
<appender name="console" class="ch.qos.logback.core.ConsoleAppender">
  <encoder>
    <pattern>%d{HH:mm:ss} %highlight([%level]) %logger{25}.%M:\t%msg%n</pattern>
  </encoder>
</appender>
```

## Explain the concept of dependency injection and its benefits in creating scalable applications.

  -

### Where do you use dependency injection in your application?

  - JPA/Hibernate.
  - Do env vars count?
  - port numbers/url in tests.

### What is the alternative to dependency injection?

  - Hardcoding? Tightly coupling.
  - DAO might have its own database connection.

### How does dependency injection help in your code?

  - Can switch out underlying database, EG. for testing.

## How do streams and lambda expressions work in Java, and how can they help in processing collections?

  - Sequence of elements supporting aggregate and parallel operations.
  - Computation on a stream called _pipeline_: Source --> Intermediate ops --> (single)
      Terminal op.
  - Constraints for stream ops:
    + must be non-interfering (they do not modify the stream source); and
    + in most cases must be stateless (their result should not depend on any state that might change during execution of the stream pipeline).


### Show an example of using streams and lambda expressions to filter, map, and reduce elements in a collection.

  ```java
  my_list.stream().filter(e -> e.name.startsWith("P")).map(e -> e.intval).sum();
  ```

### What are the advantages of using streams and lambda expressions over traditional iteration methods?

  - Conciseness
  - Flexibility
  - Readability
  - Builtin parallellism with Collection.parallelStream() (allowed to return sequential in
    spec)

### Show examples of grouping elements in a collection using streams and lambda expressions.

  ```java
  Map<Department, List<Person>> map = persons.stream().collect(groupingBy(Person::department));
  ```

## What are Java Generics, and why are they used? Provide an example of a generic class or interface.

  - java.util collections like List are generic.
  - type safe, no need for casts from/to object.

### How can generics help in creating reusable and type-safe code?

  - replace casts from/to object.

### Why do we want to use interface data types over concrete data types?

  - creates more flexible code, due to Liskov Substitution Principle.

### How can you restrict the types that can be used with a generic class or method?

  - can specify lower and upper bounds like <T extends V> and <T super V>.

## Explain the role of JPA (Java Persistence API) in Java applications and how it manages data persistence.

  - abstraction layer for database management; on top of JDBC which is database connection abstraction.

### What are Hibernate and EntityManagerFactory, and how do they facilitate ORM (Object-Relational Mapping) in Java?

  - Hibernate is an implementation (and predecessor/original idea) of JPA.
  - EntityManagerFactory is global interface for persistence unit.

### What is the purpose of the HibernateConfig file in your application?

  - to initialize persistence unit (postgres db on this address etc.)

### What is a DAO (Data Access Object), and how does it fit into the design of a backend service?

  - maps from view to model (database entities, relational objects).

## What is the purpose of some common JPA annotations

  - @Entity: tell JPA to manage this class.
  - @NToM: relationship.
  - @Id, @GeneratedValue: database ids.

### What is the purpose of using annotations like @Entity, @Table, and @Column in JPA entities?

  - specify database names of columns and tables.

### What is the difference between FetchType.LAZY and FetchType.EAGER in JPA entity relationships?

  - ...

### What is the purpose of the @Id and @GeneratedValue annotations in JPA entity classes?

  - ...

## Describe the One-to-Many relationship in a database and how it can be represented using JPA annotations.

  - ...

### What is the difference between unidirectional and bidirectional relationships in JPA entities?

  - ...

### How can cascading operations be used to maintain consistency in a One-to-Many relationship?

  - ...

### What are some common pitfalls to avoid when working with cascading operations in JPA entities?

  - need to analyze relationships between entities, or unintentional deletions or orphans may
    occur.

## Explain how external APIs can be integrated into an application and some considerations when calling these APIs.

  - ...

### What are RESTful APIs, and how can they be consumed in a Java application?

  -

### How can you handle authentication when calling external APIs?

  - jwcookie with encrypted credentials.
  - cookie with encrypted credentials.

### How can we best consume JSON data from an external API in a Java application?

  -

## What is JWT (JSON Web Token), and how does it enhance security in RESTful applications?

  -

### How can JWT be used for authentication and authorization in a RESTful API?

  -

### What are the main components of a JWT token, and how are they used for secure communication?

  -

### How can JWT tokens be validated and decoded in a Java application?

  -


## How does role-based access control work, and how can it be applied to secure REST API endpoints?

  -

### Show in your code how you can restrict access to certain endpoints based on user roles.

  -

### How are the user roles from the token used to determine access rights in your application?

  -

## Describe the differences between integration testing and unit testing in the context of REST API endpoints.

  -

### What is RestAssured, and how can it be used to test RESTful APIs in Java?

  -

### How can you test access control and error handling in your RESTful API using integration tests?

  -

### How does Hamcrest help in writing expressive assertions in your test cases?

  -

## Continuous- integration/delivery/deployment

1. continuous integration
  - builds
  - tests

Merge often, write automated tests.

2. continuous delivery
  - builds
  - tests
  - acceptance tests
  - deploy to staging (eg. github)
  - [manual] deploy to production
  - smoke tests

Semi-automated deployment needed. Strong tests.

3. continuous continuous deployment
  - builds
  - tests
  - acceptance tests
  - deploy to staging (eg. github)
  - deploy to production
  - smoke tests

Extremely fast feedback.

## JPA

 - _orphanRemoval_ is an entirely ORM-specific thing. It marks "child" entity to be
   removed when it's no longer referenced from the "parent" entity, e.g. when you remove
   the child entity from the corresponding collection of the parent entity.


## IP/TCP

 - Private Address Space

   + The Internet Assigned Numbers Authority (IANA) has reserved the
     following three blocks of the IP address space for private internets:

     1. 10.0.0.0        -   10.255.255.255  (10/8 prefix)
     2. 172.16.0.0      -   172.31.255.255  (172.16/12 prefix)
     3. 192.168.0.0     -   192.168.255.255 (192.168/16 prefix)
