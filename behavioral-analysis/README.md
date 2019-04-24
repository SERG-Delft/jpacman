# Behavioral analysis

In this folder, you can find a Python script that will help you
to do the exercise on the behavioral analysis of JPacman.

This script generates charts based on log files that are generated
via our fuzzer. See the assignment for more instructions.

## Usage

`python plotLogs.py <path/to/logfile.txt>`

## Requirements

- Python 2.7 or higher, 
- Matplotlib (+ dependencies setuptools and python-tk)

## Installation guide

The following instructions are for installing Python 2.7. You are free to choose later versions if you like.

### Linux
- Install Python
`sudo apt-get install python2.7-dev`
- Also install tkinter, which is a Python GUI toolkit
`sudo apt-get install python-tk`
- Install pip, which is one of python's packge manager
`sudo apt-get install python-pip`
- Install matplotlib
`pip install matplotlib`

If you get errors about `setuptools`, install it too
`pip install setuptools`

### Windows
- Download and run python setup from `https://www.python.org/downloads/release/python-2716/`
- Note the installation location. Add it to the PATH environment variable. For default settings, add the following to the PATH variable 
`C:\Python27` and `C:\Python27\Scripts`. 
- Restart your computer if python doesn't become available as a command in command line.
- Install pip. Save `https://bootstrap.pypa.io/get-pip.py` as `get-pip.py` and then run `python get-pip.py`
- Finally, install `setuptools` and `matplotlib`. Run `pip install setuptools` followed by `pip install matplotlib`
