# About Modmap

Modmap is a library for mapping Java objects to JSON for transfer over the wire to remote client applications.  It is
distinguished from other such libraries by being designed for the following scenario:

* The entire state accessible to the client is mapped into a single POJO in which "get" and/or query methods are used to 
  gain access to extended state.  By default, these query methods are not invoked unless the client specifically requests
  them, by sending a query string, a textual encoding of the paths from the root node to the parts of the model it is 
  interested in.  The client therefore provides a query whenever it needs to know additional information that causes the
  model to expand and provide that information.  Information it no longer needs is removed from the query string to prevent
  unnecessary data transfer.
  
* The mapper creates a complete, cacheable copy of the results of the client's query when it is executed.  This can then be 
  compared to the new results when the client query changes (or when the model changes) and only the differences transferred
  to the client.
  
This allows a design pattern of a server-side system analogous to the client-side system provided by the React javascript 
framework.  The developer can concentrate on providing a rich model object containing all the state the client needs, and update
it as required.  Modmap can then handle tracking the changes, encoding them in JSON, and transferring them to the client
where a simple module can update the client-side state simply and effectively.  All the client needs to do is map the state 
into its user interface (for which React is an ideal fit, if the client is web-based) and update its query string whenever the
information it requires changes (e.g. when views are opened or closed).

# Modmap is currently in early-stage development.  I wouldn't recommend actually using it for anything right now.
