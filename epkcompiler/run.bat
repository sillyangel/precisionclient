@echo off
title epkcompiler
java -jar CompilePackage.jar "../textures/barebones" "../compiled/web/packs/bones.epk"
java -jar CompilePackage.jar "../textures/bombies180k" "../compiled/web/packs/bombies.epk"
java -jar CompilePackage.jar "../textures/defaultnew" "../compiled/web/packs/defaultnew.epk"
java -jar CompilePackage.jar "../textures/defaultold" "../compiled/web/packs/defaultold.epk"
java -jar CompilePackage.jar "../textures/miamiprivate" "../compiled/web/packs/miamiprivate.epk"
java -jar CompilePackage.jar "../textures/modifiednew" "../compiled/web/packs/modifiednew.epk"
java -jar CompilePackage.jar "../textures/nebula" "../compiled/web/packs/nebula.epk"
java -jar CompilePackage.jar "../textures/ricefault" "../compiled/web/packs/ricefault.epk"
java -jar CompilePackage.jar "../textures/tightfault" "../compiled/web/packs/tightfault.epk"
java -jar CompilePackage.jar "../textures/walifault" "../compiled/web/packs/walifault.epk"
pause