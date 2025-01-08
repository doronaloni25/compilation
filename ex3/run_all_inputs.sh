#!/bin/bash

# Define directories
BASEDIR=$(pwd)
INPUT_DIR="$BASEDIR/input"
OUTPUT_DIR="$BASEDIR/output"
COMPILER_JAR="$BASEDIR/COMPILER"

# Create output directory if it doesn't exist
mkdir -p "$OUTPUT_DIR"

# Run the compiler for each file in the input directory
for INPUT_FILE in "$INPUT_DIR"/*; do
    # Get the filename without the directory path
    FILENAME=$(basename "$INPUT_FILE")
    
    # Set output file path
    OUTPUT_FILE="$OUTPUT_DIR/out_$FILENAME"
    
    # Run the compiler
    java -jar "$COMPILER_JAR" "$INPUT_FILE" "$OUTPUT_FILE"
done

# Compare each pair of files
compare_files() {
    if cmp -s "$1" "$2"; then
        # Files are the same
        echo -e "$1: \033[0;32mPASS\033[0m"
    else
        # Files are different
        echo -e "$1: \033[0;31mERROR\033[0m"
    fi
}

# Compare the pairs of files in the output and expected_output directories

compare_files "./output/out_TEST_01_Print_Primes.txt" "./expected_output/TEST_01_Print_Primes_Expected_Output.txt"
compare_files "./output/out_TEST_02_Bubble_Sort.txt" "./expected_output/TEST_02_Bubble_Sort_Expected_Output.txt"
compare_files "./output/out_TEST_03_Merge_Lists.txt" "./expected_output/TEST_03_Merge_Lists_Expected_Output.txt"
compare_files "./output/out_TEST_04_Matrices.txt" "./expected_output/TEST_04_Matrices_Expected_Output.txt"
compare_files "./output/out_TEST_05_Classes.txt" "./expected_output/TEST_05_Classes_Expected_Output.txt"
compare_files "./output/out_TEST_06_Print_Primes_Error.txt" "./expected_output/TEST_06_Print_Primes_Error_Expected_Output.txt"
compare_files "./output/out_TEST_07_Bubble_Sort_Error.txt" "./expected_output/TEST_07_Bubble_Sort_Error_Expected_Output.txt"
compare_files "./output/out_TEST_08_Merge_Lists_Error.txt" "./expected_output/TEST_08_Merge_Lists_Error_Expected_Output.txt"
compare_files "./output/out_TEST_09_Matrices_Error.txt" "./expected_output/TEST_09_Matrices_Error_Expected_Output.txt"
compare_files "./output/out_TEST_10_Classes_Error.txt" "./expected_output/TEST_10_Classes_Error_Expected_Output.txt"
compare_files "./output/out_TEST_11_Complex_Vars.txt" "./expected_output/TEST_11_Complex_Vars_Expected_Output.txt"
compare_files "./output/out_TEST_12_Nested_Var_d.txt" "./expected_output/TEST_12_Nested_Var_d_Expected_Output.txt"
compare_files "./output/out_TEST_13_Inheritance_Vs_Method_Overloading.txt" "./expected_output/TEST_13_Inheritance_Vs_Method_Overloading_Expected_Output.txt"
compare_files "./output/out_TEST_14_Data_Members_Access.txt" "./expected_output/TEST_14_Data_Members_Access_Expected_Output.txt"
compare_files "./output/out_TEST_15_Integral_Allocation_Size_Integral_Subscript_Exp.txt" "./expected_output/TEST_15_Integral_Allocation_Size_Integral_Subscript_Exp_Expected_Output.txt"
compare_files "./output/out_TEST_16_Complex_Vars_Error.txt" "./expected_output/TEST_16_Complex_Vars_Error_Expected_Output.txt"
compare_files "./output/out_TEST_17_Nested_Var_d_Error.txt" "./expected_output/TEST_17_Nested_Var_d_Error_Expected_Output.txt"
compare_files "./output/out_TEST_18_Inheritance_Vs_Method_Overloading_Error.txt" "./expected_output/TEST_18_Inheritance_Vs_Method_Overloading_Error_Expected_Output.txt"
compare_files "./output/out_TEST_19_Data_Members_Access_Error.txt" "./expected_output/TEST_19_Data_Members_Access_Error_Expected_Output.txt"
compare_files "./output/out_TEST_20_Integral_Allocation_Size_Integral_Subscript_Exp_Error.txt" "./expected_output/TEST_20_Integral_Allocation_Size_Integral_Subscript_Exp_Error_Expected_Output.txt"
