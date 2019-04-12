from numba import jit #leads to functions being split up into inner and outer functions due to the specific requirements needed to use jit
import numpy as np
import cv2
#import random
#import time
import sys, getopt

#Global variables of the images height and width also a list of all of the processed circles
imgW = 0
imgH = 0
done = []

#finds the centers of the given contours
def center(cir):
    centers = []
    for c in cir:
        M = cv2.moments(c)
        cx = int(M["m10"]/M["m00"])
        cy = int(M["m01"]/M["m00"])
        centers.append((cx,cy))
##        (x,y), radius = cv2.minEnclosingCircle(c)
##        centers.append((int(x), int(y)))
    return centers

#helper function for sorting lists
def sec(val):
    return val[0][0][0][1]

#helper function for sorting lists
def sec1(val):
    return val[0][0][0][0]

#helper function for sorting lists
def first(val):
    return val[0][0][0]

#helper function for sorting lists
def first1(val):
    return val[0][0][1]

#inner function of findRows()
@jit(nopython=True)
def fr(cir, centers):
    visited = []
    groups = []
    for c, cen in zip(cir, centers):
        if cen not in visited:
            temp = []
            visited.append(cen)
            temp.append(c)
            for c1, cen1 in zip(cir, centers):
                if cen1 not in visited:
                    if (abs(cen[1]-cen1[1]) < (imgH*0.00745)):#0.00591
                        visited.append(cen1)
                        temp.append(c1)
            groups.append(temp)
    return groups

#Separates circles into rows by grouping circles together in groups based on a vertical threshold
def findRows(cir):
    centers = center(cir)
    groups = fr(cir, centers)
    groups.sort(key = sec)
    return groups

#inner function of findColumns()
@jit(nopython=True)
def fc(cir, centers):
    visited = []
    groups = []
    for c, cen in zip(cir, centers):
        if cen not in visited:
            temp = []
            visited.append(cen)
            temp.append(c)
            for c1, cen1 in zip(cir, centers):
                if cen1 not in visited:
                    if (abs(cen[0]-cen1[0]) < (imgW*0.00576)):#0.00456
                        visited.append(cen1)
                        temp.append(c1)
            groups.append(temp)
    return groups

#Separates circles into columns by grouping circles together in groups based on a horizontal threshold
def findColumns(cir):
    centers = center(cir)
    groups = fc(cir, centers)
    groups.sort(key = sec1)
    return groups

#filters out groups of circles that follow the criteria for questions which is 5 circles within a certain horizontal threshold
def questions(x):
    ret = []
    for row in x:
        centers = center(row)
        n = []
        visited = []
        for r, c in zip(row, centers):
            if c not in visited and c not in done:
                temp = []
                tempvisit = []
                visited.append(c)
                tempvisit.append(c)
                temp.append(r)
                for rr,cc in zip(row, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[0] - cc[0]) < (imgW*0.0363): #.0363 .0561 .05
                            temp.append(rr)
                            tempvisit.append(cc)
                if (len(temp) == 5):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        if n: #checks for question groups that did not have a full 5 circles in rows with at least one correct group
            for r, c in zip(row, centers):
                if c not in done:
                    temp = []
                    tempvisit = []
                    tempvisit.append(c)
                    temp.append(r)
                    for rr,cc in zip(row, centers):
                        if cc not in tempvisit and cc not in done:
                            if abs(c[0] - cc[0]) < (imgW*0.0363):
                                temp.append(rr)
                                tempvisit.append(cc)
                    if (len(temp) == 3) or (len(temp) == 4):
                        for x in tempvisit:
                            done.append(x)
                        n.append(temp)
        if n:
            n.sort(key = sec1)
            ret.append(n)
    return ret

#filters out groups of circles that follow the criteria for names which is 27 circles within a certain vertical threshold
def names(x):
    ret = []
    for col in x:
        n = []
        visited = []
        centers = center(col)
        for co, c in zip(col, centers):
            if c not in visited and c not in done:
                temp = []
                tempvisit = []
                visited.append(c)
                tempvisit.append(c)
                temp.append(co)
                for coo,cc in zip(col, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[1] - cc[1]) < (imgH*0.2667995):
                            temp.append(coo)
                            tempvisit.append(cc)
                if (len(temp) == 27):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        for co, c in zip(col, centers):
            if c not in done:
                temp = []
                tempvisit = []
                tempvisit.append(c)
                temp.append(co)
                for coo,cc in zip(col, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[1] - cc[1]) < (imgH*0.2667995):
                            temp.append(coo)
                            tempvisit.append(cc)
                if (len(temp) == 26) or (len(temp) == 25):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        if n:
            ret.append(n)
    return ret

#filters out groups of circles that follow the criteria for grade/EDU level which is 17 circles within a certain vertical threshold
def gradeEDU(x):
    ret = []
    for col in x:
        n = []
        visited = []
        centers = center(col)
        for co, c in zip(col, centers):
            if c not in visited and c not in done:
                temp = []
                tempvisit = []
                visited.append(c)
                tempvisit.append(c)
                temp.append(co)
                for coo,cc in zip(col, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[1] - cc[1]) < (imgH*0.16797):
                            temp.append(coo)
                            tempvisit.append(cc)
                if (len(temp) == 17):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        for co, c in zip(col, centers):
            if c not in done:
                temp = []
                tempvisit = []
                tempvisit.append(c)
                temp.append(co)
                for coo,cc in zip(col, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[1] - cc[1]) < (imgH*0.16797):
                            temp.append(coo)
                            tempvisit.append(cc)
                if (len(temp) == 16) or (len(temp) == 15):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        if n:
            ret.append(n)
    return ret

#filters out groups of circles that follow the criteria for month of the date which is 12 circles within a certain vertical threshold
#insures that there will be only one month
def month(x):
    ret = []
    for col in x:
        if not ret:
            n = []
            visited = []
            centers = center(col)
            for co, c in zip(col, centers):
                if c not in visited and c not in done:
                    temp = []
                    tempvisit = []
                    visited.append(c)
                    tempvisit.append(c)
                    temp.append(co)
                    for coo,cc in zip(col, centers):
                        if cc not in tempvisit and cc not in done:
                            if abs(c[1] - cc[1]) < (imgH*0.1222):
                                temp.append(coo)
                                tempvisit.append(cc)
                    if (len(temp) == 12):
                        for x in tempvisit:
                            done.append(x)
                        n.append(temp)
            if not n:
                for co, c in zip(col, centers):
                    if c not in done:
                        temp = []
                        tempvisit = []
                        tempvisit.append(c)
                        temp.append(co)
                        for coo,cc in zip(col, centers):
                            if cc not in tempvisit and cc not in done:
                                if abs(c[1] - cc[1]) < (imgH*0.1222):
                                    temp.append(coo)
                                    tempvisit.append(cc)
                        if (len(temp) == 11):
                            for x in tempvisit:
                                done.append(x)
                            n.append(temp)
            if n:
                ret.append(n)
    return ret

#filters out groups of circles that follow the criteria for the normal date and ID which is 10 circles within a certain vertical threshold
def dateID(x):
    ret = []
    for col in x:
        n = []
        visited = []
        centers = center(col)
        for co, c in zip(col, centers):
            if c not in visited and c not in done:
                temp = []
                tempvisit = []
                visited.append(c)
                tempvisit.append(c)
                temp.append(co)
                for coo,cc in zip(col, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[1] - cc[1]) < (imgH*0.1015):
                            temp.append(coo)
                            tempvisit.append(cc)
                if (len(temp) == 10):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        for co, c in zip(col, centers):
            if c not in done:
                temp = []
                tempvisit = []
                tempvisit.append(c)
                temp.append(co)
                for coo,cc in zip(col, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[1] - cc[1]) < (imgH*0.1015):
                            temp.append(coo)
                            tempvisit.append(cc)
                if (len(temp) == 9) or (len(temp) == 8):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        if n:
            ret.append(n)
    return ret

#filters out groups of circles that follow the criteria for the ten group of the day from the date which is 4 circles within a certain vertical threshold
def date1(x):
    ret = []
    for col in x:
        n = []
        visited = []
        centers = center(col)
        for co, c in zip(col, centers):
            if c not in visited and c not in done:
                temp = []
                tempvisit = []
                visited.append(c)
                tempvisit.append(c)
                temp.append(co)
                for coo,cc in zip(col, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[1] - cc[1]) < (imgH*0.041):
                            temp.append(coo)
                            tempvisit.append(cc)
                if (len(temp) == 4):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        for co, c in zip(col, centers):
            if c not in done:
                temp = []
                tempvisit = []
                tempvisit.append(c)
                temp.append(co)
                for coo,cc in zip(col, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[1] - cc[1]) < (imgH*0.041):
                            temp.append(coo)
                            tempvisit.append(cc)
                if (len(temp) == 3):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        if n:
            ret.append(n)
    return ret

#filters out groups of circles that follow the criteria for gender which is 2 circles within a certain vertical threshold
def gender(x):
    ret = []
    for col in x:
        n = []
        visited = []
        centers = center(col)
        for co, c in zip(col, centers):
            if c not in visited and c not in done:
                temp = []
                tempvisit = []
                visited.append(c)
                tempvisit.append(c)
                temp.append(co)
                for coo,cc in zip(col, centers):
                    if cc not in tempvisit and cc not in done:
                        if abs(c[1] - cc[1]) < (imgH*0.021486):
                            temp.append(coo)
                            tempvisit.append(cc)
                if (len(temp) == 2):
                    for x in tempvisit:
                        done.append(x)
                    n.append(temp)
        if n:
            ret.append(n)
    return ret

#inner function of filterDublicates
@jit(nopython=True)
def fd(cir, centers):
    filt = []
    visited = []
    for x, y in zip(cir, centers):
        if y not in visited:
            visited.append(y)
            filt.append(x)
            for a,b in zip(cir, centers):
                if b not in visited: 
                    if abs(y[0] - b[0]) < (imgW*0.00910194) and abs(y[1] - b[1]) < (imgH*0.01172):
                        visited.append(b)
    return filt

#filters out circles with the centers within a certain distance of each other
def filterDublicates(cir):
    centers = center(cir)
    return fd(cir, centers)

#filters out contours that follow the criteria of circle
def findCircles(contour):
    cir = []
    for cont in contour:                #0.0303
        approx = cv2.approxPolyDP(cont, 0.02*cv2.arcLength(cont, True), True)
        area = cv2.contourArea(cont)     #0.000065                                  0.000533
        if ((len(approx) > 6) and (area > (imgW*imgH*0.00007)) and (area < (imgW*imgH*0.0005))):
            (x,y,w,h) = cv2.boundingRect(cont)
            ratio = float(w)/ float(h)
            if ((ratio >= .8) and (ratio <= 1.267)):
                cir.append(cont)
    return cir

#used to sort filled in bubbles/circles
def sortKeyQ(val):
    return val[0]

#finds the filled in bubbles of each question group but if the question group does not have a full 5 circles then "error" is added to the list
#also sorts the selected bubbles from most filled in to least filled in
def findSelectedQ(qG):
    bubbled = []
    for i,row in enumerate(qG):
        bubbled.append([])
        for j,group in enumerate(row):
            group.sort(key = first)
            bubbled[i].append([(0,-1)])
            if (len(group) == 5) :
                for k,c in enumerate(group):
                    mask = np.zeros(img.shape, dtype="uint8")
                    cv2.drawContours(mask, [c], -1, 255, -1)

                    mask = cv2.bitwise_and(img, img, mask=mask)
                    total = cv2.countNonZero(mask)
                        #total > bubbled[i][j][0]
                    if total > (cv2.contourArea(c) * .8): #total > (imgW*imgH*0.000036)
                        if bubbled[i][j][0][0] == 0:
                            bubbled[i][j][0] = (total, k)
                        else:
                            bubbled[i][j].append((total, k))
                bubbled[i][j].sort(key = sortKeyQ)
                bubbled[i][j].reverse()
            else:
                bubbled[i][j] = [(0, "error")]
    return bubbled

#finds the darkest bubble of the vertical groups
def findSelected(g):
    bubbled = []
    for i,col in enumerate(g):
        bubbled.append([])
        for j,group in enumerate(col):
            group.sort(key = first1)
            bubbled[i].append((0,-1))
            if (len(group) == 27) or (len(group) == 17) or (len(group) == 12) or (len(group) == 10) or (len(group) == 4) or (len(group) == 2):
                for k,c in enumerate(group):
                    mask = np.zeros(img.shape, dtype="uint8")
                    cv2.drawContours(mask, [c], -1, 255, -1)

                    mask = cv2.bitwise_and(img, img, mask=mask)
                    total = cv2.countNonZero(mask)

                    if total > bubbled[i][j][0] and total > (cv2.contourArea(c) * .8): #total > (imgW*imgH*0.000065)
                        bubbled[i][j] = (total, k)
            else:
                bubbled[i][j] = (0, "error")
    return bubbled

#start = time.time()

#default comand line argument values
fileName = "scan.jpg"

#parse command line arguments
try:
    opts, args = getopt.getopt(sys.argv[1:], "hf:", ["file="])
except getopt.GetoptError:
    print("-f filename")
    sys.exit(2)
for opt, arg in opts:
    if opt == "-h":
        print("-f filename")
        system.exit()
    elif opt in ("-f", "--file"):
        fileName = arg

#load images and apply filters
img = cv2.imread(fileName)
imgW = img.shape[1]
imgH = img.shape[0]
kernel = np.ones((5,5), np.uint8)
#img = cv2.resize(img, (imgW, imgH), interpolation = cv2.INTER_AREA)
img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
sharp = cv2.filter2D(img, -1, np.array([[-1,-1,-1], [-1,9,-1], [-1,-1,-1]]))
sharp = cv2.GaussianBlur(sharp, (3,3), 0)
sharp = cv2.Canny(sharp, 60, 120)
dank = cv2.Canny(img, 60, 120)
#img = cv2.GaussianBlur(img, (3,3), 0)
img = cv2.bilateralFilter(img, 7, 40, 40)
#img = cv2.bilateralFilter(img, 1, 40, 40)
dark = cv2.threshold(img, 110, 255, cv2.THRESH_BINARY)[1]
#img = cv2.threshold(img, 1, 255, cv2.THRESH_TOZERO)[1]
#cv2.namedWindow("e", cv2.WINDOW_NORMAL)
#cv2.imshow("e", img)
dark = cv2.Canny(dark, 1, 36)
#dark = cv2.dilate(dark, kernel, iterations=1)
#dark = cv2.erode(dark, kernel, iterations=1)
#dark = cv2.morphologyEx(dark, cv2.MORPH_CLOSE, kernel)
edges = cv2.Canny(img, 1, 60)
#kernel = np.ones((5,5), np.uint8)
#edges = cv2.dilate(edges,kernel, iterations=1)
#edges = cv2.erode(edges,kernel, iterations=1)
edges1 = cv2.morphologyEx(edges, cv2.MORPH_CLOSE, kernel)
img = cv2.threshold(img, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)[1]

#finds the edges of the images
con = cv2.findContours(img, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)[0]
contours = cv2.findContours(edges, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)[0]
co = cv2.findContours(dark, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)[0]
cdank = cv2.findContours(dank, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)[0]
contours1 = cv2.findContours(edges1, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)[0]
cont = cv2.findContours(sharp, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)[0]

##img1 = cv2.imread(fileName)
##img1 = cv2.resize(img1, (imgW, imgH), interpolation = cv2.INTER_AREA)
#cv2.drawContours(img1, co, -1, (0, 250, 250), 2)

#puts all contours in a single list
contours = np.append(contours, con, axis=0)
contours = np.append(contours, co, axis=0)
contours = np.append(contours, cdank, axis=0)
contours = np.append(contours, contours1, axis=0)
contours = np.append(contours, cont, axis=0)

circles = findCircles(contours)

circles = filterDublicates(circles)

#cv2.drawContours(img1, circles, -1, (0, 0, 255), 3)

rows = findRows(circles)
columns = findColumns(circles)

nameGroup = names(columns)

##for i in nameGroup:
##   for j in i:
##       cv2.drawContours(img1, j, -1, (random.randint(0,255), random.randint(0,255), random.randint(0,255)), 2)
    
eduGroup = gradeEDU(columns)

##for i in eduGroup:
##   for j in i:
##       cv2.drawContours(img1, j, -1, (random.randint(0,255), random.randint(0,255), random.randint(0,255)), 2)

monthGroup = month(columns)

##for i in monthGroup:
##   for j in i:
##       cv2.drawContours(img1, j, -1, (random.randint(0,255), random.randint(0,255), random.randint(0,255)), 2)

dateIDGroup = dateID(columns)

##for i in dateIDGroup:
##   for j in i:
##       cv2.drawContours(img1, j, -1, (random.randint(0,255), random.randint(0,255), random.randint(0,255)), 2)

queGroup = questions(rows)

##for i in queGroup:
##   for j in i:
##       cv2.drawContours(img1, j, -1, (random.randint(0,255), random.randint(0,255), random.randint(0,255)), 2)

dateGroup = date1(columns)

##for i in dateGroup:
##   for j in i:
##       cv2.drawContours(img1, j, -1, (random.randint(0,255), random.randint(0,255), random.randint(0,255)), 2)

genderGroup = gender(columns)

##for i in genderGroup:
##   for j in i:
##       cv2.drawContours(img1, j, -1, (random.randint(0,255), random.randint(0,255), random.randint(0,255)), 2)

bubbledQue = findSelectedQ(queGroup)

bubbledName = findSelected(nameGroup)

bubbledEDU = findSelected(eduGroup)

bubbledMonth = findSelected(monthGroup)

bubbledDate = findSelected(dateGroup)

bubbledDateID = findSelected(dateIDGroup)

bubbledGender = findSelected(genderGroup)

##for i in rows:
##    cv2.drawContours(img1, i, -1, (random.randint(0,255), random.randint(0,255), random.randint(0,255)), 4)

##for i in columns:
##    cv2.drawContours(img1, i, -1, (random.randint(0,255), random.randint(0,255), random.randint(0,255)), 4)

#prints out values but if there is an no selected circle in question selection then -1 is printed out
#error is printed out for an error reading questions
for z in bubbledName:
    for w in z:
        print("n " + str(w[1]))

for z in bubbledGender:
    for w in z:
        print("g " + str(w[1]))

for z in bubbledEDU:
    for w in z:
        print("e " + str(w[1]))

for z in bubbledMonth:
    for w in z:
        print("m " + str(w[1]))

for z in bubbledDate:
    for w in z:
        print("d " + str(w[1]))

for z in bubbledDateID:
    for w in z:
        print("i " + str(w[1]))

i = -1
for z in bubbledQue:
    if z:
        if len(z) >= 5:
            i += 1
            for j,w in enumerate(z):
                selected = [str(x[1]) for x in w]
                print(str(i) + "-" + str(j) + " " + ''.join(selected))
          
##cv2.namedWindow("edges", cv2.WINDOW_NORMAL)
##cv2.imshow("edges", img1)
##cv2.namedWindow("edges1", cv2.WINDOW_NORMAL)
##cv2.imshow("edges1", img)
##cv2.namedWindow("edges2", cv2.WINDOW_NORMAL)
##cv2.imshow("edges2", edges)
##cv2.namedWindow("edges3", cv2.WINDOW_NORMAL)
##cv2.imshow("edges3", sharp)

#print(time.time() - start)
##cv2.waitKey(0)
##cv2.destroyAllWindows()
