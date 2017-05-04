# optimized-quicksort
Quick sort with a couple of minor tweaks

Takes the median of 3, protecting against the worst case where the pivot is all the way to one side (also the initial shuffle is not necessary). Then uses a cut-off, switching over to insertion sort when the subset is small enough.
