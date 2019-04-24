import sendEmail
import sys, getopt

address = ""
csv = ""

#parse command line arguments
try:
    opts, args = getopt.getopt(sys.argv[1:], "ha:c:", ["address=","csv="])
except getopt.GetoptError:
    print("-a emailAddress -c csvName")
    sys.exit(2)
for opt, arg in opts:
    if opt == "-h":
        print("-a address")
        system.exit()
    elif opt in ("-a", "--address"):
        address = arg
    elif opt in ("-c", "--csv"):
        csv = arg

if csv:
    sendEmail.SendEmail.sendEmailOnFilesProcessed(address, csv)
    #print("processed " + address + " " + csv)
else:
    sendEmail.SendEmail.sendEmailOnFilesRecieved(address)
    #print("recieved " + address + " " + csv)
