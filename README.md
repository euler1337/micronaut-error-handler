For some reason the order of server filters matters wrt MDC, MDC entries set in HI prio filters leak.

`MdcControllerTest.lowprio` works, but `MdcControllerTest.hiprio`fails.
