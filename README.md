# Distributed Systems Front-End

## Members: Dimitris Tzovanis, Elpida Stasinou


This is an application that runs on Android devices and provides an interface for the system. Through this the user:
- Selects a GPX file stored on their device and send it to the backend for asynchronous processing.
- Is able to receive a notification from the Master that the processing is finished and will be able to show
  the results of the GPX processing (total distance, average speed, total climb and total time).
- Thy are also be able to see his personal stats (Total Exercise Time, Total Distance and Total Climb)
  that they have done and graphically display the difference between them and the overall average (e.g. they have run a total of 24% more than the average).
- The communication between the Application and the Master is done exclusively using TCP Sockets.
  The Application connects with a TCP Socket to the Master. Through this Socket the GPX is sent.
  The Master sends back the processing results via the same Socket which remains open until they are received. T
  his process is implemented using Threads so that the application remains interactive until the results are received.
