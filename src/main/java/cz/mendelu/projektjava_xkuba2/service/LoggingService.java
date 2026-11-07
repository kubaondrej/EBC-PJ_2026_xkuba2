package cz.mendelu.projektjava_xkuba2.service;

public final class LoggingService {


    public static void log(String message)
    {
        //predpokladam ze tohle preskoci tuhle aktualni metodu? Asi? TODO: Objasnit
        StackWalker.StackFrame caller = StackWalker.getInstance().walk(s -> s.skip(1).findFirst()).orElse(null);
        String methodName = (caller != null) ? caller.getMethodName() : "neznama_trida";
        String className = (caller != null) ? caller.getClassName() : "neznama_trida";

        System.out.println("[" + className + " - " + methodName + "] " + message);
    }
}
