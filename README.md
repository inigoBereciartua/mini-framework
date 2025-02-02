# Mini-Framework

A minimalistic Java framework for learning and experimenting with core concepts found in enterprise frameworks such as Spring. This project implements basic dependency injection, component scanning, lifecycle management, and a generalized AOP infrastructure for cross-cutting concerns like logging and security.

## Purpose

The main goal of this project is to deepen your understanding of how modern Java frameworks work under the hood. Key areas explored include:
- **Dependency Injection (DI):**  
  Automatically discovering and wiring components.
- **Lifecycle Management:**  
  Managing bean creation, post-construction initialization, and destruction via annotations like `@PostConstruct` and `@PreDestroy`.
- **Aspect-Oriented Programming (AOP):**  
  Intercepting method calls to apply cross-cutting concerns using dynamic proxies, including a generalized advice system that supports logging and security.

## Features

- **Component Scanning:**  
  Automatically scans a specified package for classes annotated with `@Component`.
- **Dependency Injection:**  
  Supports field injection using a custom `@Autowired` annotation.
- **Bean Scopes:**  
  Differentiates between singleton and prototype beans using a `@Scope` annotation.
- **Lifecycle Callbacks:**  
  Executes methods annotated with `@PostConstruct` upon bean initialization and `@PreDestroy` upon shutdown.
- **Generalized AOP:**  
  Provides an AOP infrastructure with:
  - A generic `Advice` interface for before and after method interception.
  - Pre-built advices (e.g., `LoggingAdvice` and `SecurityAdvice`) that demonstrate security checks (using `@PreAuthorize` and `@PostAuthorize`) and logging.
  - A dynamic proxy-based mechanism to chain multiple advices.

## Getting Started

### Prerequisites

- Java 21
- Maven for building the project

### Running the Application
Navigate to the AspectOrientedApplication class in the com.ibereciartua.examples.aspectOrientedSecurity package and run its main method. This will instantiate the application context, perform dependency injection, and demonstrate AOP-based security and logging.

## Future Enhancements
- **Dynamic Advice Registration:**
Add configuration support to dynamically register and order advices.
- **Expression Language Integration:**
Integrate a simple expression language to dynamically evaluate conditions in security annotations.
- **Class-Based Proxies:**
Incorporate CGLIB or a similar library to support proxying classes that do not implement interfaces.
- **Expanded Lifecycle Management:**
Further refine bean scopes and lifecycle callbacks.

## Conclusion
This mini-framework is designed to help you explore and understand key enterprise Java concepts by building a simplified version of a modern framework. It serves as a learning tool that demonstrates DI, AOP, and lifecycle management in a modular and extensible manner.