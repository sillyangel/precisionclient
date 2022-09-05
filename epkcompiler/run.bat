@echo off
title epkcompiler
java -jar CompilePackage.jar {"../lwjgl-rundir/packs/1.5" "../compiled/web/packs/1.5.epk"} {java -jar CompilePackage.jar "../lwjgl-rundir/packs/1.5" "../compiled/web/packs/1.5.epk"}
pause