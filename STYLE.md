# 6712 BulletProof Java Style Guide
###### Revision 0 (WIP)
- [Introduction](#introduction)
- [File Basics](#file-basics)
    - [Source File Basics](#source-file-basics)
- [Formatting](#formatting)

## Introduction
The purpose of this style guide is to provide a standard of code that is consistant and easy to read. This guide will insure that your code is professional-looking and readable on the many devices and screen sizes we work with.

---

## File Basics
- File name: The source file name will match the case-sensitive name of the top-level class it contains.
- File encoding: UTF-8
- Whitespace characters: Spaces, not tabs, will be used for indentation and general whitespace.
- Column Width: The max column width is 120 characters. All lines must be line-wrapped if they exceed this number.

---

### Source File Basics
- Package statement: The package statement will not be line-wrapped.
- Import statements: Import statements will not be line-wrapped.
    - Imports will be ordered as follows:
        1. All standard library imports
        2. All other third party imports
        3. All project imports
        4. All static imports
    - One or two blank lines will follow each block.
    - There are no other blank lines between imports.
    - Within each block, imports will be in an alphabetical order.
- Class declaration: Each top-level class resides in its own source file.
    - Class member ordering: Class members will be ordered in _some logical order_.
    - Method names: When a class has multiple methods with the same name, and/or multiple constructors, they will appear sequentially.

---

### Formatting
- Braces: Braces are always used with `` if ``, `` else ``, `` while ``, `` for ``, `` do `` statements, even if the body contains one line, or is empty.
    - Line breaks <!-- TODO(Garrison): Examples -->
        - Line break before the opening brace
        - Line break after the opening brace
        - Line break before closing brace
        - Line break after the closing brace
