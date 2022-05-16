# Environment

This folder contains code regarding the simulated environment used in this project.

This environment contains 3D data regarding the structure of itself, as well as node information regarding points of interest for the objective at hand (be it mineral nodes, fires in a forest or other such information).

## Development

In order to develop and deploy this system, we have implemented this within a Docker container, in order to properly communicate with each of device containers.

### Docker

In order to deploy this system, a image is built with the developed code for each system. This base image can then be customized to receive input parameters and files.