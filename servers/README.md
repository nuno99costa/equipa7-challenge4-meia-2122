# Servers

This folder contains code for receiving and analysing data as well as organizing actuator actions.

## Types

### Scan Analysis

This system is tasked with receiving the data from scanner devices and analysing said data in order to detect points of interest. This system is also capable of organizing a cluster of scanner devices in order to optimize and enhance results from said devices.

### Action Generator

This system is tasked with received points of interest from the scan analysis server and to provide task to the actuator devices in an efficient manner.

## Development

In order to develop these systems, an initial Docker image was used to independently implement each given system.

### Docker

In order to deploy these systems, a image is built with the developed code for each system. This base image can then be customized to receive input parameters.