months_day = [31,28,31,30,31,30,31,31,30,31,30,31];
now_year=1900;
now_day=0;
res=0;
for i in range (1900,1999):
  for j in range (0,11):
      if now_day == 6:
        res=res+1
      if  j == 2 and (i % 4 == 0 or i % 400 == 0):
        now_day=(now_day+29)%7
        continue
      else:
        now_day=(now_day+months_day[j])%7
print(res)

