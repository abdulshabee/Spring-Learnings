# 🌱 Spring Bean Lifecycle and Scopes

> A comprehensive guide to understanding how Spring manages beans from birth to destruction

---

## 📖 What is a Spring Bean?

A **Spring Bean** is any object that is instantiated, assembled, and managed by the Spring IoC (Inversion of Control) container.

### 🏨 Hotel Analogy

| Spring Concept | Hotel Equivalent |
|----------------|------------------|
| Bean | A prepared dish |
| IoC Container | Kitchen and kitchen staff |
| Bean Definition | Recipe card |
| Bean Instance | Actual plated food |
| Bean Scope | How long the dish stays fresh |

---

## 🔄 Spring Bean Lifecycle

### Complete Lifecycle Flow Diagram

─────────────────────────────────────────────────────────────────┐
│ SPRING BEAN LIFECYCLE │
│ From Birth to Destruction │
└─────────────────────────────────────────────────────────────────┘

Phase 1: Bean Definition Loaded
│
▼
Phase 2: Bean Instantiated (Constructor Called)
│
▼
Phase 3: Dependencies Injected (Setters/Fields Called)
│
▼
Phase 4: BeanPostProcessor - Before Initialization
│
▼
Phase 5: Initialization Callbacks
│
▼
Phase 6: BeanPostProcessor - After Initialization
│
▼
Phase 7: Bean Ready for Use (In ApplicationContext)
│
│ (Application processes requests...)
│
▼
Phase 8: Container Shutdown Triggered
│
▼
Phase 9: Destruction Callbacks
│
▼
Phase 10: Bean Destroyed (Garbage Collected)

text

---

### Phase-by-Phase Breakdown

#### 📋 Phase 1: Bean Definition Loading

| Aspect | Description |
|--------|-------------|
| **What Happens** | Spring scans classpath, reads configuration, creates BeanDefinition objects |
| **Input** | Annotations (@Component, @Service), XML files, Java @Configuration classes |
| **Output** | BeanDefinition objects in registry |
| **Analogy** | Writing recipe cards for each dish |

**Metadata Stored in BeanDefinition:**
- ✅ Fully qualified class name
- ✅ Scope (singleton, prototype, etc.)
- ✅ Dependencies needed
- ✅ Initialization method name
- ✅ Destruction method name
- ✅ Lazy initialization flag
- ✅ Factory method details (if any)

---

#### 🏗️ Phase 2: Bean Instantiation

| Aspect | Description |
|--------|-------------|
| **What Happens** | Container calls constructor to create object instance |
| **Prerequisites** | All constructor arguments must be resolvable |
| **Result** | Raw object in memory (dependencies not yet injected) |
| **Analogy** | Cooking the main dish following recipe |

**Instantiation Methods:**

| Method | Description | Use Case |
|--------|-------------|----------|
| Constructor | Direct constructor call | Most common approach |
| Static Factory Method | Call static method that returns instance | Legacy code, third-party libraries |
| Instance Factory Method | Call method on existing bean | Complex creation logic |
| FactoryBean Interface | Specialized factory pattern | Framework-level components |

---

#### 💉 Phase 3: Dependency Injection

| Aspect | Description |
|--------|-------------|
| **What Happens** | Container injects all declared dependencies |
| **Order** | Constructor args → Setter methods → Field injection |
| **Result** | Fully wired object with all dependencies |
| **Analogy** | Adding garnishes, sauces, and side dishes |

**Injection Order Details:**
Step 1: Constructor Arguments (During instantiation)
↓
Step 2: Setter Methods (After instantiation, if any)
↓
Step 3: Field Injection (After instantiation, via reflection)
↓
Result: Fully populated bean

text

---

#### 🔧 Phase 4: BeanPostProcessor - Before Initialization

| Aspect | Description |
|--------|-------------|
| **What Happens** | All BeanPostProcessor's `postProcessBeforeInitialization()` methods run |
| **Key Activity** | Proxy creation for AOP (@Transactional, @Cacheable, @Async) |
| **Analogy** | Chef inspects dish, adds final touches before serving |

**Common Uses:**
- ✅ Creating dynamic proxies
- ✅ Applying AOP advice
- ✅ Wrapping beans with interceptors
- ✅ Custom annotation processing
- ✅ Bean validation before initialization

---

#### 🚀 Phase 5: Initialization Callbacks

| Aspect | Description |
|--------|-------------|
| **What Happens** | Initialization methods called in specific order |
| **Purpose** | Custom setup logic, resource allocation |
| **Analogy** | Adding final garnish, ringing service bell |

**Three Ways to Define Initialization (Executed in This Order):**

| Priority | Method | How to Define |
|----------|--------|---------------|
| 1st | @PostConstruct | Annotation on method (JSR-250) |
| 2nd | InitializingBean | Implement `afterPropertiesSet()` interface |
| 3rd | Custom init-method | `@Bean(initMethod = "methodName")` |

**Typical Initialization Tasks:**
- ✅ Opening database connections
- ✅ Starting background threads
- ✅ Pre-loading cache data
- ✅ Validating configuration
- ✅ Registering with external services

---

#### ✅ Phase 6: BeanPostProcessor - After Initialization

| Aspect | Description |
|--------|-------------|
| **What Happens** | All BeanPostProcessor's `postProcessAfterInitialization()` methods run |
| **Purpose** | Final wrapping, proxy finalization |
| **Analogy** | Final quality check before dish leaves kitchen |

---

#### 🟢 Phase 7: Bean Ready for Use

| Aspect | Description |
|--------|-------------|
| **Status** | Bean fully initialized and available |
| **Location** | Stored in ApplicationContext |
| **Access** | Via `@Autowired`, `getBean()`, or injection |
| **Analogy** | Dish ready to be served to customers |

**For Singleton Beans:**
- Cached in singleton cache
- Same instance returned for all requests

**For Prototype Beans:**
- No caching
- New instance created per request

---

#### 🔴 Phase 8: Container Shutdown

| Aspect | Description |
|--------|-------------|
| **Trigger** | Application shutdown, context.close() |
| **What Happens** | Container begins destruction sequence |
| **Analogy** | Restaurant closing time announced |

---

#### 🧹 Phase 9: Destruction Callbacks

| Aspect | Description |
|--------|-------------|
| **What Happens** | Destruction methods called in specific order |
| **Purpose** | Clean up resources, release connections |
| **Analogy** | Washing dishes, cleaning station |

**Three Ways to Define Destruction (Executed in This Order):**

| Priority | Method | How to Define |
|----------|--------|---------------|
| 1st | @PreDestroy | Annotation on method (JSR-250) |
| 2nd | DisposableBean | Implement `destroy()` interface |
| 3rd | Custom destroy-method | `@Bean(destroyMethod = "methodName")` |

**Typical Destruction Tasks:**
- ✅ Closing database connections
- ✅ Stopping background threads
- ✅ Flushing buffers
- ✅ Releasing file handles
- ✅ Deregistering from external services

**⚠️ Important Note for Prototype Beans:**
> Spring does NOT manage the complete lifecycle of prototype beans. Destruction callbacks are NOT called automatically. You must manually release resources.

---

#### ♻️ Phase 10: Bean Destroyed

| Aspect | Description |
|--------|-------------|
| **Status** | Bean eligible for garbage collection |
| **Analogy** | Dish disposed, kitchen closed |

---

## 🎯 Bean Scopes

### What is a Bean Scope?

> Bean scope defines the lifecycle and visibility of a bean instance within the Spring IoC container context.

### Scope Overview Table

| Scope | Instances | Created | Destroyed | Web Only |
|-------|-----------|---------|-----------|----------|
| **Singleton** | 1 per container | Startup | Shutdown | ❌ No |
| **Prototype** | New each time | On request | GC | ❌ No |
| **Request** | 1 per HTTP request | Request start | Response sent | ✅ Yes |
| **Session** | 1 per HTTP session | Session start | Session end | ✅ Yes |
| **Application** | 1 per ServletContext | Startup | Shutdown | ✅ Yes |
| **WebSocket** | 1 per WS session | Connection | Disconnect | ✅ Yes |

---

### 1️⃣ Singleton Scope (Default)

#### Overview

| Property | Value |
|----------|-------|
| **Instances per Container** | Exactly 1 |
| **Created When** | Application startup (eager by default) |
| **Destroyed When** | Container shutdown |
| **Thread Safety** | Must be thread-safe (shared instance) |
| **State Recommendation** | Stateless recommended |
| **Default in Spring** | ✅ Yes |

#### 🏨 Analogy: Restaurant's Head Chef
- Only one head chef per restaurant
- Everyone interacts with the same head chef
- Head chef exists until restaurant closes

#### ✅ When to Use

- Service classes with business logic
- Repository/DAO classes
- Controllers (Spring MVC)
- Configuration classes
- Utility classes without state
- Connection pools
- Thread pools

#### ❌ When NOT to Use

- Objects with user-specific state
- Objects that hold request data
- Objects that need frequent recreation
- Stateful beans with mutable shared data
- Objects that are not thread-safe

#### 📊 Characteristics

| Aspect | Detail |
|--------|--------|
| **Memory Impact** | Low (single instance) |
| **Performance** | Best (reused, no creation overhead) |
| **Creation Time** | Startup (eager) or first request (lazy) |
| **Caching** | Stored in singleton cache |

#### ⚠️ Potential Issues

| Issue | Cause | Solution |
|-------|-------|----------|
| Race conditions | Shared mutable state | Make stateless or use synchronization |
| Memory leaks | Holding references | Clear references in @PreDestroy |
| Data corruption | Concurrent modifications | Use thread-safe collections |

---

### 2️⃣ Prototype Scope

#### Overview

| Property | Value |
|----------|-------|
| **Instances per Request** | New instance each time |
| **Created When** | On each injection or getBean() call |
| **Destroyed When** | Garbage collected (no container tracking) |
| **Thread Safety** | Inherent (separate instances) |
| **State Recommendation** | Can be stateful |
| **Default in Spring** | ❌ No |

#### 🏨 Analogy: Fast Food Burger
- Fresh burger made for each order
- Each customer gets their own burger
- Burger exists only until eaten (garbage collected)

#### ✅ When to Use

- Stateful beans with per-operation data
- Objects that need unique state per use
- Temporary helper objects
- Non-thread-safe objects
- Objects with heavy customization per use
- Command objects in command pattern

#### ❌ When NOT to Use

- Stateless services (wasteful)
- Objects that are expensive to create
- Objects with shared resources
- Objects that hold connections

#### 📊 Characteristics

| Aspect | Detail |
|--------|--------|
| **Memory Impact** | High per request (GC dependent) |
| **Performance** | Overhead of constant creation |
| **Destruction** | Not managed by Spring |
| **Lifecycle Management** | Manual cleanup required |

#### ⚠️ Critical Warnings

| Warning | Explanation |
|---------|-------------|
| **No Destruction Callbacks** | @PreDestroy NOT called automatically |
| **Resource Leaks** | Must manually close connections/files |
| **Singleton Injection Issue** | Singleton gets only one prototype instance |

---

### 3️⃣ Request Scope (Web Only)

#### Overview

| Property | Value |
|----------|-------|
| **Instances per Request** | 1 per HTTP request |
| **Created When** | HTTP request begins |
| **Destroyed When** | HTTP response sent |
| **Thread Safety** | Safe (confined to request thread) |
| **State Recommendation** | Can hold request-specific data |
| **Web Application Only** | ✅ Yes |

#### 🏨 Analogy: Restaurant Order Ticket
- New ticket for each table order
- Ticket valid only for that order's lifecycle
- Discarded after order is complete

#### ✅ When to Use

- Controllers needing request data
- Request data holder objects
- Request-scoped caching
- Validation objects per request
- Audit logging per request
- Request correlation ID holders

#### ❌ When NOT to Use

- In non-web applications
- For long-lived data
- For data shared across requests
- In background threads not tied to request
- For large data objects

#### 📊 Characteristics

| Aspect | Detail |
|--------|--------|
| **Lifecycle** | Tied to HTTP request |
| **Cleanup** | Automatic after response |
| **Proxy Required** | Yes, for singleton injection |
| **Memory** | Per-request, cleared quickly |

---

### 4️⃣ Session Scope (Web Only)

#### Overview

| Property | Value |
|----------|-------|
| **Instances per Session** | 1 per HTTP session |
| **Created When** | Session created (first request) |
| **Destroyed When** | Session timeout or invalidation |
| **Thread Safety** | Not thread-safe (multiple requests) |
| **State Recommendation** | Holds user-specific data |
| **Web Application Only** | ✅ Yes |

#### 🏨 Analogy: Hotel Room Key Card
- Guest gets one card for their stay
- Card works for entire stay duration
- Card invalid after checkout

#### ✅ When to Use

- User preferences object
- Shopping cart data
- User authentication context
- Multi-step wizard data
- User session tracking
- Theme/display preferences

#### ❌ When NOT to Use

- Large objects (memory pressure)
- Frequently changing data (use request scope)
- Data needed across all users (use singleton)
- Temporary calculation results
- Sensitive data that should not persist

#### 📊 Characteristics

| Aspect | Detail |
|--------|--------|
| **Default Timeout** | 30 minutes (configurable) |
| **Memory Impact** | Multiplied by active sessions |
| **Serialization** | Required for distributed sessions |
| **Concurrent Access** | Multiple requests can access |

#### ⚠️ Important Considerations

| Consideration | Impact |
|---------------|--------|
| **Session Replication** | Must implement Serializable |
| **Clustering** | Beans must survive serialization |
| **Memory Management** | Monitor active session count |
| **Thread Safety** | Synchronize mutable state access |

---

### 5️⃣ Application Scope (Web Only)

#### Overview

| Property | Value |
|----------|-------|
| **Instances per Application** | 1 per ServletContext |
| **Created When** | Application startup |
| **Destroyed When** | Application shutdown |
| **Thread Safety** | Must be thread-safe |
| **State Recommendation** | Application-wide shared data |
| **Web Application Only** | ✅ Yes |

#### 🏨 Analogy: Restaurant Menu
- Same menu for all customers
- Menu exists for restaurant lifetime
- Updated menu affects everyone

#### ✅ When to Use

- Application configuration cache
- Shared reference data
- Application metrics collector
- Global counters or statistics
- Read-only lookup tables

#### 📊 Singleton vs Application Scope

| Aspect | Singleton | Application |
|--------|-----------|-------------|
| **Container Scope** | Per Spring Container | Per ServletContext |
| **Typical Behavior** | Similar in single context | Similar in single context |
| **Multiple Contexts** | Different instances | Same instance |

---

### 6️⃣ WebSocket Scope (WebSocket Only)

#### Overview

| Property | Value |
|----------|-------|
| **Instances per Connection** | 1 per WebSocket session |
| **Created When** | WebSocket connection established |
| **Destroyed When** | Connection closed |
| **Thread Safety** | Not inherently thread-safe |
| **WebSocket Only** | ✅ Yes |

#### 🏨 Analogy: Phone Call Connection
- Each call has dedicated connection
- Connection exists for call duration
- Disconnected when call ends

#### ✅ When to Use

- Chat session data
- Real-time game state per player
- WebSocket connection metadata
- Temporary conversation state

---

## 🔗 Scope Mixing: Common Problems and Solutions

### Problem 1: Singleton Bean Injecting Prototype Bean

#### The Issue
| Aspect | Description |
|--------|-------------|
| **Scenario** | Singleton service needs fresh prototype bean per use |
| **What Happens** | Prototype created only once (when singleton created) |
| **Result** | Prototype becomes effectively singleton |

#### Why This Happens
Singleton creation at startup:
→ Dependencies resolved once
→ Prototype instance created and injected
→ Same prototype instance reused forever

text

#### Solutions

| Solution | Description | Recommendation |
|----------|-------------|----------------|
| **ObjectFactory / Provider** | Inject factory interface, call getObject() | ⭐ Preferred |
| **@Lookup Method** | Abstract method returning prototype | ✅ Good |
| **Proxy Mode** | @Scope with proxyMode = TARGET_CLASS | ✅ Good |
| **ApplicationContext.getBean()** | Direct container access | ⚠️ Last resort |

---

### Problem 2: Singleton Injecting Request/Session Scope

#### The Issue
| Aspect | Description |
|--------|-------------|
| **Scenario** | Singleton service needs request-scoped data |
| **What Happens** | No active request during singleton creation |
| **Result** | BeanCreationException or incorrect data |

#### Solutions

| Solution | Description | Recommendation |
|----------|-------------|----------------|
| **Scoped Proxy** | Inject proxy that resolves per request | ⭐ Preferred |
| **RequestContextHolder** | Access request attributes directly | ✅ Good for simple data |
| **Method Parameters** | Pass request data as arguments | ⭐ Best (cleanest) |
| **ObjectFactory** | Deferred resolution | ✅ Good |

---

### Problem 3: Session Scope Serialization for Clustering

#### The Issue
| Aspect | Description |
|--------|-------------|
| **Scenario** | Application deployed in clustered environment |
| **Requirement** | Session data must replicate across nodes |
| **Challenge** | Session-scoped beans must be serializable |

#### Requirements Checklist

- ✅ Bean class implements `Serializable`
- ✅ All non-transient fields are Serializable
- ✅ Transient fields reinitialized after deserialization
- ✅ Consider serialVersionUID
- ✅ Test serialization/deserialization cycle

---

## 📊 Scope Decision Matrix

### Which Scope Should You Choose?

| If You Need... | Choose This Scope |
|----------------|-------------------|
| Shared service with business logic | **Singleton** |
| Database repository or DAO | **Singleton** |
| Temporary calculation helper | **Prototype** |
| User-specific shopping cart | **Session** |
| Request correlation ID | **Request** |
| Per-request validation | **Request** |
| Application-wide configuration | **Singleton** |
| User preferences | **Session** |
| Stateful command execution | **Prototype** |
| Global metrics counter | **Singleton** or **Application** |
| WebSocket connection data | **WebSocket** |

---

## 🎯 Best Practices Summary

### Singleton Scope
| Do ✅ | Don't ❌ |
|-------|---------|
| Keep stateless | Store request/user data |
| Make thread-safe if stateful | Use for mutable shared state |
| Use for most services | Hold references to prototype beans |
| Pre-load cache data | Depend on request/session beans directly |

### Prototype Scope
| Do ✅ | Don't ❌ |
|-------|---------|
| Use for stateful operations | Use for stateless services |
| Clear state after use | Rely on destruction callbacks |
| Use ObjectFactory for injection | Create expensive objects repeatedly |
| Release resources manually | Hold connections indefinitely |

### Request Scope
| Do ✅ | Don't ❌ |
|-------|---------|
| Store request-specific data | Use outside web context |
| Let container manage lifecycle | Share across threads |
| Use for audit/correlation data | Store large objects |
| Inject as proxy when needed | Access after response committed |

### Session Scope
| Do ✅ | Don't ❌ |
|-------|---------|
| Implement Serializable | Store large objects |
| Monitor memory usage | Forget about session timeout |
| Synchronize mutable access | Store sensitive data indefinitely |
| Clean up on logout | Ignore clustering requirements |

---

## ✅ Self-Assessment Checklist

### Bean Lifecycle Knowledge

- [ ] Can describe all 10 lifecycle phases in order
- [ ] Understand when BeanPostProcessors execute
- [ ] Know difference between @PostConstruct and InitializingBean
- [ ] Understand why @PreDestroy may not be called for prototype beans
- [ ] Can explain proxy creation timing
- [ ] Know three ways to define initialization callbacks
- [ ] Know three ways to define destruction callbacks

### Bean Scopes Knowledge

- [ ] Can list all 6 bean scopes
- [ ] Know which scopes are web-only
- [ ] Understand singleton vs prototype trade-offs
- [ ] Can explain the singleton-injecting-prototype problem
- [ ] Know how to handle request-scoped beans in async methods
- [ ] Understand session scope serialization requirements
- [ ] Can choose appropriate scope for given scenarios

### Practical Application

- [ ] Shopping cart implementation: Session scope ✅
- [ ] Database connection pool: Singleton scope ✅
- [ ] User's language preference: Session scope ✅
- [ ] Temporary calculation result: Prototype scope ✅
- [ ] REST controller: Singleton scope ✅
- [ ] Request logging context: Request scope ✅
- [ ] WebSocket chat session: WebSocket scope ✅

---

## 📚 Related Topics for Further Study

- **BeanPostProcessor Interface** - Custom bean processing
- **BeanFactoryPostProcessor** - Configuration modification
- **AOP and Proxying Mechanisms** - How scoped proxies work
- **Thread Scope** - Custom scope for thread-local beans
- **Spring Boot DevTools** - Development-time scope behavior
- **Reactive Scopes** - ApplicationScope, RequestScope in WebFlux

---

## 📖 Memory Aid

### Remember with "C-I-S-D"

| Letter | Meaning | Key Point |
|--------|---------|-----------|
| **C** | Create | Beans created by container, not by you |
| **I** | Inject | Dependencies provided, not created |
| **S** | Scope | Defines lifecycle and visibility |
| **D** | Destroy | Clean up resources before destruction |

### Scope Duration Mnemonic
Singleton - Forever (application lifetime)
Prototype - Fleeting (per use)
Request - Brief (per HTTP request)
Session - Visit (per user session)
Application - Eternal (servlet context lifetime)
WebSocket - Connected (per connection)

text

---

*Last Updated: 12/04/2026

**Remember:** *Understanding bean lifecycle and scopes is fundamental to avoiding memory leaks, thread safety issues, and performance problems in Spring applications.*
This README provides a complete, well-structured reference for Spring Bean Lifecycle and Scopes suitable for documentation, study, or GitHub profile display.

