For some reason the wrong error handler (of a parent class) is triggered instead of the correct error handler for the subclass.
This only happens when the parent class error handler is defined above the subclass error handler in the file. 

`ControllerTest.testHandlerFirst` works, but `ControllerTest.testHandlerLast`fails.
