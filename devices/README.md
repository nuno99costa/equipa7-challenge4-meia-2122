# Devices

This folder contains code for controlling the ROS based systems. 

Each of these systems implement a myriad of parameters in order to properly describe each component, from batteries to sensors and communication capabilities. Such components are:

- Inert Components (e.g. Batteries)
- Sensory Components (e.g. Cameras)
- Actuator Components (e.g. Mining apparatus)

## Types

### Scanner

The scanner system is tasked with imaging a given environment and providing positional data to the overall cluster.

### Actuator

The actuator system is tasked with performing a given action on the environment, based on the data provided by the scanner system.

## Development

In order to develop these systems, an initial Docker image was used to independently implement each given system, taking into account the possibility of various system of the same type in a given cluster.

### Docker

In order to deploy these systems, a image is built with the developed code for each system, based on the osrf/ros2 image available at [DockerHub](https://hub.docker.com/r/osrf/ros2/). This base image can then be customized to receive input parameters.