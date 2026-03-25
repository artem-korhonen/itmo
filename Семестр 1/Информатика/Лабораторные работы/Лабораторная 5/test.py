import seaborn
import matplotlib.pyplot as plt
 
 
seaborn.set(style='whitegrid')
fmri = seaborn.load_dataset("fmri")
 
seaborn.boxplot(x="timepoint",
                y="signal",
                data=fmri)
plt.show()